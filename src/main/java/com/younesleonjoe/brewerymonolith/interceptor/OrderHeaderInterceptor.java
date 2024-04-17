package com.younesleonjoe.brewerymonolith.interceptor;

import com.younesleonjoe.brewerymonolith.entity.BeerOrder;
import com.younesleonjoe.brewerymonolith.enums.OrderStatusEnum;
import com.younesleonjoe.brewerymonolith.event.BeerOrderStatusChangeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/*
 * Catch order updates
 * Put a hook on hibernate to capture a change of event
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderHeaderInterceptor implements Interceptor {

  private final ApplicationEventPublisher publisher;

  @Override
  public boolean onFlushDirty(
      Object entity,
      Object id,
      Object[] currentState,
      Object[] previousState,
      String[] propertyNames,
      Type[] types)
      throws CallbackException {

    if (entity instanceof BeerOrder) {
      for (Object curObj : currentState) {
        if (curObj instanceof OrderStatusEnum) {
          for (Object prevObj : previousState) {
            if (prevObj instanceof OrderStatusEnum prevStatus) {
              OrderStatusEnum curStatus = (OrderStatusEnum) curObj;

              if (curStatus != prevStatus) {
                log.debug("Order status change detected");

                publisher.publishEvent(
                    new BeerOrderStatusChangeEvent((BeerOrder) entity, prevStatus));
              }
            }
          }
        }
      }
    }
    return Interceptor.super.onFlushDirty(
        entity, id, currentState, previousState, propertyNames, types);
  }
}
