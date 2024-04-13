package com.younesleonjoe.brewerymonolith.listener;

import com.younesleonjoe.brewerymonolith.entity.BeerInventory;
import com.younesleonjoe.brewerymonolith.entity.BeerOrder;
import com.younesleonjoe.brewerymonolith.entity.BeerOrderLine;
import com.younesleonjoe.brewerymonolith.enums.OrderStatusEnum;
import com.younesleonjoe.brewerymonolith.event.NewBeerOrderEvent;
import com.younesleonjoe.brewerymonolith.repository.BeerInventoryRepository;
import com.younesleonjoe.brewerymonolith.repository.BeerOrderRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewBeerOrderListener {

  private final BeerOrderRepository beerOrderRepository;
  private final BeerInventoryRepository beerInventoryRepository;

  @Async
  @EventListener
  @Transactional
  // require keyword synchronized to prevent errors
  public synchronized void listen(NewBeerOrderEvent event) {

    log.debug("NewBeerOrderListener received event: {}", event);

    AtomicInteger totalOrdered = new AtomicInteger();
    AtomicInteger totalAllocated = new AtomicInteger();

    BeerOrder beerOrder = beerOrderRepository.findOneById(event.getBeerOrder().getId());

    if (beerOrder == null) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException exception) {
        exception.printStackTrace();
      }
      beerOrder = beerOrderRepository.findOneById(event.getBeerOrder().getId());
    }

    for (BeerOrderLine beerOrderLine : beerOrder.getBeerOrderLines()) {
      if ((beerOrderLine.getOrderQuantity() - beerOrderLine.getQuantityAllocated()) > 0) {
        allocateBeerOrderLine(beerOrderLine);
      }
      totalOrdered.set(totalOrdered.get() + beerOrderLine.getOrderQuantity());
      totalAllocated.set(totalAllocated.get() + beerOrderLine.getQuantityAllocated());
    }

    if (totalOrdered.get() == totalAllocated.get()) {
      log.debug(
          "NewBeerOrderListener Completed Order Allocation: " + beerOrder.getCustomerReference());
      beerOrder.setOrderStatus(OrderStatusEnum.READY);
    }

    beerOrderRepository.saveAndFlush(beerOrder);
  }

  private void allocateBeerOrderLine(BeerOrderLine beerOrderLine) {
    List<BeerInventory> beerInventoryList =
        beerInventoryRepository.findAllByBeer(beerOrderLine.getBeer());

    for (BeerInventory beerInventory : beerInventoryList) {
      int inventory = beerInventory.getQuantityOnHand();
      int orderQty = beerOrderLine.getOrderQuantity();
      int allocatedQty = beerOrderLine.getQuantityAllocated();
      int qtyToAllocate = orderQty - allocatedQty;

      if (inventory >= qtyToAllocate) { // full allocation
        inventory = inventory - qtyToAllocate;
        beerOrderLine.setQuantityAllocated(orderQty);
        beerInventory.setQuantityOnHand(inventory);
      } else if (inventory > 0) { // partial allocation
        beerOrderLine.setQuantityAllocated(allocatedQty + inventory);
        beerInventory.setQuantityOnHand(0);
      }
    }

    beerInventoryRepository.saveAll(beerInventoryList);

    beerInventoryList.stream()
        .filter(beerInventory -> beerInventory.getQuantityOnHand() == 0)
        .forEach(beerInventoryRepository::delete);
  }
}
