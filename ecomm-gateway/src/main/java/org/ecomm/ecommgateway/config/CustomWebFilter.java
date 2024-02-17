package org.ecomm.ecommgateway.config;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommgateway.rest.auth0.Auth0ServiceClient;
import org.ecomm.ecommgateway.rest.controller.PostsWebSocketHandler;
import org.ecomm.ecommgateway.rest.model.UserInfo;
import org.ecomm.ecommgateway.rest.model.WSEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

@Configuration
@Slf4j
public class CustomWebFilter implements WebFilter {

  @Autowired RouterValidator routerValidator;

  @Autowired Auth0ServiceClient auth0ServiceClient;

  @Autowired JwsUtil jwsUtil;

  @Autowired PostsWebSocketHandler postsWebSocketHandler;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();

    if (routerValidator.isSecured.test(request)) {
      if (this.isAuthMissing(request)) {
        log.info("Auth header is missing");
        return this.onError(exchange, Constants.AUTH_HEADER_MISSING);
      }

      String authHeader = request.getHeaders().getOrEmpty("Authorization").get(0);
      String tokenType = request.getHeaders().getOrEmpty("x-token-type").get(0);

      if (StringUtil.isNullOrEmpty(tokenType)) {
        tokenType = "app";
      }

      UserInfo userInfo = auth0ServiceClient.getUserInfo(authHeader.split(" ")[1], tokenType);

      return Mono.fromCallable(() -> jwsUtil.getAuthentication(authHeader.split(" ")[1]))
          .subscribeOn(Schedulers.boundedElastic())
          .flatMap(
              authentication ->
                  chain
                      .filter(
                          exchange
                              .mutate()
                              .request(
                                  exchange
                                      .getRequest()
                                      .mutate()
                                      .header("x-auth0-user-email", userInfo.getEmail())
                                      .build())
                              .build())
                                            .then(
                                                Mono.fromRunnable(
                                                    () -> {
                                                      // ServerHttpResponse response =
//                       exchange.getResponse();

                                                      postsWebSocketHandler.broadcast(
                                                          WSEvent.builder()
                                                              .message(
                                                                  String.format(
                                                                      "%s is trying the API %s",
                                                                      userInfo.getEmail(),
                       request.getURI()))
                                                              .build());
                                                    }))
                      .contextWrite(
                          ReactiveSecurityContextHolder.withAuthentication(authentication)))
          .then();
    }
    return chain.filter(exchange);
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err) {
    log.info("User is unauthorised ::: {}", err);
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    return response.setComplete();
  }

  private boolean isAuthMissing(ServerHttpRequest request) {
    return !request.getHeaders().containsKey("Authorization");
  }
}
