package org.ecomm.ecommuser.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecomm.ecommuser.rest.model.User;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.stream.Stream;

public class Utility {

    public static <T> Stream<T> stream(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return Stream.empty();
        } else {
            return collection.stream();
        }
    }

    public static User getLoggedInUser() {
        ObjectMapper objectMapper = new ObjectMapper();

        User user;
        try {
            user = objectMapper.readValue(MDC.get("user"), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

}
