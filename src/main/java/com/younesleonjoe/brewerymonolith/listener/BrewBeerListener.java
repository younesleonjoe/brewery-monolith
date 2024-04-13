package com.younesleonjoe.brewerymonolith.listener;

import com.younesleonjoe.brewerymonolith.entity.Beer;
import com.younesleonjoe.brewerymonolith.entity.BeerInventory;
import com.younesleonjoe.brewerymonolith.event.BrewBeerEvent;
import com.younesleonjoe.brewerymonolith.event.NewInventoryEvent;
import com.younesleonjoe.brewerymonolith.repository.BeerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BrewBeerListener {

  private final BeerRepository beerRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Async
  @EventListener
  @Transactional
  public void listen(BrewBeerEvent event) {

    log.debug("BrewBeerListener received event: {}", event);

    Beer beer = beerRepository.getReferenceById(event.getBeer().getId());

    BeerInventory beerInventory =
        BeerInventory.builder().beer(beer).quantityOnHand(beer.getQuantityToBrew()).build();

    beer.getBeerInventory().add(beerInventory);

    Beer savedBeer = beerRepository.save(beer);

    log.debug("BrewBeerListener saved beer: {}", savedBeer);

    eventPublisher.publishEvent(new NewInventoryEvent(beer));
  }
}
