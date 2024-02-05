package org.ecomm.ecommgateway.rest.auth0;

import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommgateway.config.JwsUtil;
import org.ecomm.ecommgateway.rest.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Auth0ServiceClient {

  @Value("${auth0.userinfo.url}")
  String userInfoUrl;

  @Autowired
  JwsUtil jwsUtil;

  public UserInfo getUserInfo(String token, String tokenType) {

      if(tokenType.equals("app")){
        return jwsUtil.verifyAppToken(token);
      } else {
        return jwsUtil.validateAuth0Token(token);
      }
  }
}
