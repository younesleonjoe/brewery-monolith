package com.younesleonjoe.brewerymonolith.event;

import com.younesleonjoe.brewerymonolith.entity.BeerOrder;
import org.springframework.context.ApplicationEvent;

public class NewBeerOrderEvent extends ApplicationEvent {

  public NewBeerOrderEvent(BeerOrder source) {
    super(source);
  }

  public BeerOrder getBeerOrder() {
    return (BeerOrder) getSource();
  }
}
