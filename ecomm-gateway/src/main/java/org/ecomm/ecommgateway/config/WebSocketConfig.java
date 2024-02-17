package org.ecomm.ecommgateway.config;

import org.ecomm.ecommgateway.rest.controller.EchoWebSocketHandler;
import org.ecomm.ecommgateway.rest.controller.PostsWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfig {

  @Autowired PostsWebSocketHandler postsWebSocketHandler;

  @Bean
  public HandlerMapping handlerMapping() {
    Map<String, WebSocketHandler> map = new HashMap<>();
    map.put("/ws/events", postsWebSocketHandler);

    SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
    mapping.setUrlMap(map);
    return mapping;
  }
}
