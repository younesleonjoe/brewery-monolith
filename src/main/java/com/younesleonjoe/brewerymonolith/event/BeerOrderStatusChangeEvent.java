package com.younesleonjoe.brewerymonolith.event;

import com.younesleonjoe.brewerymonolith.entity.BeerOrder;
import com.younesleonjoe.brewerymonolith.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
public class BeerOrderStatusChangeEvent extends ApplicationEvent {

  private final OrderStatusEnum previousStatus;

  public BeerOrderStatusChangeEvent(BeerOrder source, OrderStatusEnum previousStatus) {
    super(source);
    this.previousStatus = previousStatus;
  }

  public BeerOrder getBeerOrder() {
    return (BeerOrder) getSource();
  }
}
