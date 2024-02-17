package org.ecomm.ecommgateway.config;
import java.security.Key;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommgateway.exception.ErrorCodes;
import org.ecomm.ecommgateway.exception.ErrorResponse;
import org.ecomm.ecommgateway.exception.JWTException;
import org.ecomm.ecommgateway.persistence.entity.UserCredentials;
import org.ecomm.ecommgateway.rest.model.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@Component
public class JwsUtil {

  @Value("${jwt.secret-key}")
  String secretKey;

  static final long JWT_EXPIRATION = 60 * 60 * 1000;

  static final long REFRESH_EXPIRATION = 3 * 24 * 60 * 60 * 1000;

  @Value("${auth0.issuer}")
  String issuer;

  public UserInfo validateAuth0Token(String authToken) {
    JwkProvider provider = new UrlJwkProvider(issuer);
    UserInfo.UserInfoBuilder userInfoBuilder = UserInfo.builder();
    try {
      DecodedJWT jwt = JWT.decode(authToken);
      // Get the kid from received JWT token
      Jwk jwk = provider.get(jwt.getKeyId());

      Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);

      JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();

      jwt = verifier.verify(authToken);

      log.info("jwt is {}", jwt.getClaims());

      userInfoBuilder
          .sub(jwt.getClaims().get("sub").asString())
          .email(jwt.getClaims().get("email").asString())
          .name(jwt.getClaims().get("name").asString())
          .picture(jwt.getClaims().get("picture").asString());

    } catch (Exception e) {
      // Invalid signature/claims
      log.error("Invalid JWT claim {}", e.getMessage());

      throw new JWTException(
          HttpStatus.UNAUTHORIZED,
          ErrorResponse.builder()
              .message(e.getMessage())
              .code(ErrorCodes.UNAUTHORIZED_ACCESS)
              .build());
    }
    return userInfoBuilder.build();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserCredentials userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(Map<String, Object> extraClaims, UserCredentials userDetails) {
    return buildToken(extraClaims, userDetails, JWT_EXPIRATION);
  }

  public String generateRefreshToken(UserCredentials userDetails) {
    return buildToken(new HashMap<>(), userDetails, REFRESH_EXPIRATION);
  }

  private String buildToken(
      Map<String, Object> extraClaims, UserCredentials userDetails, long expiration) {

    List<String> stringStream =
        userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    return Jwts.builder()
        .setClaims(Map.of("email", userDetails.getUsername(), "roles", stringStream))
        .setSubject(userDetails.getSubject())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public UserInfo verifyAppToken(String token) {

    Claims body = getClaims(token);

    return mapClaimsToUserInfo(body);
  }

  private Claims getClaims(String token) {
    Claims body;
    try {
      body =
          Jwts.parserBuilder()
              .setSigningKey(getSignInKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
    } catch (ExpiredJwtException e) {
      throw new JWTException(
          HttpStatus.UNAUTHORIZED, ErrorResponse.builder().message("JWT token is expired").code("jwt_expired").build());
    } catch (UnsupportedJwtException e) {
      throw new JWTException(
          HttpStatus.UNAUTHORIZED,
          ErrorResponse.builder().message("JWT format is incorrect").build());
    } catch (MalformedJwtException e) {
      throw new JWTException(
          HttpStatus.UNAUTHORIZED,
          ErrorResponse.builder().message("JWT token is malformed").build());
    } catch (SignatureException e) {
      throw new JWTException(
          HttpStatus.UNAUTHORIZED,
          ErrorResponse.builder().message("JWT token has incorrect signature").build());
    }
    return body;
  }

  public Authentication getAuthentication(String token) {
    Claims claims = getClaims(token);

    String roles =
        (String) claims.get("roles", List.class).stream().collect(Collectors.joining(","));

    Collection<? extends GrantedAuthority> authorities =
        AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  private UserInfo mapClaimsToUserInfo(Claims claims) {
    UserInfo userInfo = new UserInfo();
    userInfo.setSub(claims.getSubject());
    userInfo.setNickName(claims.get("nickname", String.class));
    userInfo.setName(claims.get("name", String.class));
    userInfo.setPicture(claims.get("picture", String.class));
    userInfo.setEmail(claims.get("email", String.class));
    // userInfo.setEmailVerified(claims.get("email_verified", Boolean.class));
    return userInfo;
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {

    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
