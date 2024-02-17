package org.ecomm.ecommproduct.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommproduct.persistance.entity.RInventory;
import org.ecomm.ecommproduct.persistance.repository.InventoryRedisRepository;
import org.ecomm.ecommproduct.persistance.repository.InventoryRepository;
import org.ecomm.ecommproduct.rest.model.Inventory;
import org.ecomm.ecommproduct.utils.RedisUtils;
import org.ecomm.ecommproduct.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Configuration
@Slf4j
public class RedisInit {

  @Autowired
  RedisUtils redisUtils;



  @PostConstruct
  public void init() {

    redisUtils.initializeRedis();

  }
}
