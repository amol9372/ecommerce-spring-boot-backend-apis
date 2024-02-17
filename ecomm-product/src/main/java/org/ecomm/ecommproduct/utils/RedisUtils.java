package org.ecomm.ecommproduct.utils;

import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommproduct.persistance.entity.RInventory;
import org.ecomm.ecommproduct.persistance.repository.InventoryRedisRepository;
import org.ecomm.ecommproduct.persistance.repository.InventoryRepository;
import org.ecomm.ecommproduct.rest.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class RedisUtils {

  @Autowired InventoryRedisRepository inventoryRedisRepository;

  @Autowired InventoryRepository inventoryRepository;

  @Autowired ExecutorService executorService;

  @Autowired ConcurrentLinkedDeque<RInventory> concurrentLinkedDeque;

  @Autowired RedisTemplate<String, String> redisTemplate;

  public void initializeRedis() {
    var inventories = inventoryRepository.findAll();

    List<RInventory> rInventories =
        Utility.stream(inventories)
            .map(
                eInventory ->
                    RInventory.builder()
                        .inventory(
                            Inventory.builder()
                                .quantityAvailable(eInventory.getQuantityAvailable())
                                .sku(eInventory.getSku())
                                .id(eInventory.getId())
                                .variantId(eInventory.getVariant().getId())
                                .quantitySold(eInventory.getQuantitySold())
                                .build())
                        .variantId(eInventory.getVariant().getId())
                        .build())
            .toList();

    try {
      inventoryRedisRepository.deleteAll();
      inventoryRedisRepository.saveAll(rInventories);
    } catch (Exception e) {

      log.info("Unable to connect to Redis ::: {}", e.getMessage());
    }
  }

  public void addChangeToQueue(RInventory rInventory) {

    concurrentLinkedDeque.push(rInventory);

    RInventory inventory = concurrentLinkedDeque.peek();
    log.info("The top element at queue is ::: {}", inventory);
    log.info("Queue size ::: {}", concurrentLinkedDeque.size());
  }

  @Scheduled(fixedDelay = 3 * 60000)
  public void syncQueueWithDB() {
    log.info("Sync redis with DB cron");
    Utility.stream(concurrentLinkedDeque)
        .forEach(
            item -> inventoryRepository.updateInventory(
                item.getInventory().getQuantityAvailable(), item.getInventory().getSku()));

    concurrentLinkedDeque.clear();
  }

  public Boolean checkRedisConnection() {
    RedisConnection connection = null;
    try {

      connection = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();

      connection.ping();
      log.info("Redis connection is active.");
      connection.close();

      return true;
    } catch (Exception e) {
      if(Objects.nonNull(connection)){
        connection.close();
      }
      log.info("Redis connection failed ::: {}", e.getMessage());
      return false;
    }
  }
}
