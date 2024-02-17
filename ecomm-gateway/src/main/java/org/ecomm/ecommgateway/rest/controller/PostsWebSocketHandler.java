package org.ecomm.ecommgateway.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.ecomm.ecommgateway.rest.model.WSEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PostsWebSocketHandler implements WebSocketHandler {

  private final List<WebSocketSession> connectedSessions = new CopyOnWriteArrayList<>();


  @Override
  public List<String> getSubProtocols() {
    return List.of("test");
  }

  @Override
  public Mono<Void> handle(WebSocketSession session) {
    connectedSessions.add(session);

    session.receive()
            .doOnTerminate(() -> connectedSessions.remove(session)) // Remove session on disconnect
            .subscribe();

    String protocol = session.getHandshakeInfo().getSubProtocol();
    return doSend(session);
  }

  private Mono<Void> doSend(WebSocketSession session) {
    Flux<WebSocketMessage> heartbeat =
        Flux.interval(Duration.ofSeconds(120)).map(tick -> session.textMessage("connected"));

    return session.send(heartbeat);
  }

  public void broadcast(WSEvent event)  {
    for (WebSocketSession session : connectedSessions) {

      ObjectMapper mapper = new ObjectMapper();
        String jsonMessage = null;
        try {
            jsonMessage = mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        session.send(Mono.just(session.textMessage(jsonMessage))).subscribe();
    }
  }
}
