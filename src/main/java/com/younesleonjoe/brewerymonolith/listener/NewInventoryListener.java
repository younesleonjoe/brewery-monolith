package com.younesleonjoe.brewerymonolith.listener;

import com.younesleonjoe.brewerymonolith.entity.BeerOrder;
import com.younesleonjoe.brewerymonolith.enums.OrderStatusEnum;
import com.younesleonjoe.brewerymonolith.event.NewBeerOrderEvent;
import com.younesleonjoe.brewerymonolith.event.NewInventoryEvent;
import com.younesleonjoe.brewerymonolith.repository.BeerOrderRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewInventoryListener {

  private final BeerOrderRepository beerOrderRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Async
  @EventListener
  @Transactional
  public void listen(NewInventoryEvent event) {

    log.debug("NewInventoryListener received event: {}", event);

    List<BeerOrder> newBeerOrderList =
        beerOrderRepository.findAllByOrderStatus(OrderStatusEnum.NEW);
    List<BeerOrder> readyBeerOrderList =
        beerOrderRepository.findAllByOrderStatus(OrderStatusEnum.READY);

    log.debug("NewInventoryListener fetched NewBeerOrderList: {}", newBeerOrderList);
    log.debug("NewInventoryListener fetched ReadyBeerOrderList: {}", readyBeerOrderList);

    for (BeerOrder beerOrder : newBeerOrderList) {
      log.debug("NewInventoryListener Republishing NewBeerOrderEvent for BeerOrder: {}", beerOrder);
      eventPublisher.publishEvent(new NewBeerOrderEvent(beerOrder));
    }
  }
}
