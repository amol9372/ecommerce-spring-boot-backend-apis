package org.ecomm.ecommgateway.config;

import org.ecomm.ecommgateway.exception.JWTException;
import org.ecomm.ecommgateway.exception.ResourceNotFoundException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class CustomGlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

  /**
   * Create a new {@code AbstractErrorWebExceptionHandler}.
   *
   * @param errorAttributes the error attributes
   * @param resources the resources configuration properties
   * @param applicationContext the application context
   * @since 2.4.0
   */
  public CustomGlobalExceptionHandler(
      ErrorAttributes errorAttributes,
      WebProperties.Resources resources,
      ApplicationContext applicationContext,
      ServerCodecConfigurer configurer) {
    super(errorAttributes, resources, applicationContext);
    this.setMessageWriters(configurer.getWriters());
  }

  @Override
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
    ErrorAttributeOptions options = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE);
    Map<String, Object> errorPropertiesMap = getErrorAttributes(request, options);
    Throwable throwable = getError(request);
    HttpStatusCode httpStatus = determineHttpStatus(throwable);

    errorPropertiesMap.put("status", httpStatus.value());
    errorPropertiesMap.remove("error");
    errorPropertiesMap.remove("requestId");
    errorPropertiesMap.remove("timestamp");

    return ServerResponse.status(httpStatus)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(errorPropertiesMap));
  }

  private HttpStatusCode determineHttpStatus(Throwable throwable) {
    if (throwable instanceof ResponseStatusException) {
      return ((ResponseStatusException) throwable).getStatusCode();
    } else if (throwable instanceof JWTException || throwable instanceof BadCredentialsException || throwable instanceof UsernameNotFoundException) {
      return HttpStatus.UNAUTHORIZED;
    } else if (throwable instanceof ResourceNotFoundException) {
      return HttpStatus.UNPROCESSABLE_ENTITY;
    } else {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }
}
