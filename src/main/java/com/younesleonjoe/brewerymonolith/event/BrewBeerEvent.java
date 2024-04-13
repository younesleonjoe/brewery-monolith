package com.younesleonjoe.brewerymonolith.event;

import com.younesleonjoe.brewerymonolith.entity.Beer;
import org.springframework.context.ApplicationEvent;

public class BrewBeerEvent extends ApplicationEvent {

  public BrewBeerEvent(Beer source) {
    super(source);
  }

  public Beer getBeer() {
    return (Beer) getSource();
  }
}
