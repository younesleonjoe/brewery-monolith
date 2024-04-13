package com.younesleonjoe.brewerymonolith.event;

import com.younesleonjoe.brewerymonolith.entity.Beer;
import org.springframework.context.ApplicationEvent;

public class NewInventoryEvent extends ApplicationEvent {

  public NewInventoryEvent(Beer source) {
    super(source);
  }

  public Beer getBeer() {
    return (Beer) getSource();
  }
}
