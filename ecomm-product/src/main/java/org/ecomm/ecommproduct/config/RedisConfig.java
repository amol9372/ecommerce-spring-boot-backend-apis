package org.ecomm.ecommproduct.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
class RedisConfig {

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
    jedisConFactory.setHostName("${host.url}");
    jedisConFactory.setPort(6379);
    return jedisConFactory;
  }

  @Bean
  RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
  }
}
