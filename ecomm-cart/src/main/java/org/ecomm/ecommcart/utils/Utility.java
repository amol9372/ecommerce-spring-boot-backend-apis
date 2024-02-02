package org.ecomm.ecommcart.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.stream.Stream;
import org.springframework.util.CollectionUtils;

public class Utility {

  public static <T> Stream<T> stream(Collection<T> collection) {
    if (CollectionUtils.isEmpty(collection)) {
      return Stream.empty();
    } else {
      return collection.stream();
    }
  }

  public static Object convertKeyValueToObject(Object keyValueObject) {
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonString = keyValueObject.toString();
    try {
      return objectMapper.readValue(jsonString, Object.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
