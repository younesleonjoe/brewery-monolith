package com.younesleonjoe.brewerymonolith.service;

import com.younesleonjoe.brewerymonolith.entity.Beer;
import com.younesleonjoe.brewerymonolith.event.BrewBeerEvent;
import com.younesleonjoe.brewerymonolith.repository.BeerRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

  private final BeerRepository beerRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  @Scheduled(fixedRate = 5000) // runs every 5 seconds
  @Override
  public void checkInventory() {

    List<Beer> beerList = beerRepository.findAll();

    for (Beer beer : beerList) {

      AtomicInteger inventoryQuantityOnHand = new AtomicInteger();

      beer.getBeerInventory()
          .forEach(inventory -> inventoryQuantityOnHand.addAndGet(inventory.getQuantityOnHand()));

      log.debug(
          "BeerInventory: "
              + beer.getName()
              + " : QuantityOnHand = "
              + inventoryQuantityOnHand.get());

      if (beer.getMinOnHand() >= inventoryQuantityOnHand.get()) {
        // brew beer
        eventPublisher.publishEvent(new BrewBeerEvent(beer));
      }
    }
  }
}
