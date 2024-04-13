package com.younesleonjoe.brewerymonolith.listener;

import com.younesleonjoe.brewerymonolith.dto.OrderStatusUpdateDTO;
import com.younesleonjoe.brewerymonolith.entity.BeerOrder;
import com.younesleonjoe.brewerymonolith.event.BeerOrderStatusChangeEvent;
import com.younesleonjoe.brewerymonolith.mapper.DateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class BeerOrderStatusChangeEventListener {

  private final RestTemplate restTemplate;
  private final DateMapper dateMapper;

  public BeerOrderStatusChangeEventListener(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
    this.dateMapper = new DateMapper();
  }

  @Async
  @EventListener
  public void listen(BeerOrderStatusChangeEvent event) {

    BeerOrder order = event.getBeerOrder();
    OrderStatusUpdateDTO updateDTO =
        OrderStatusUpdateDTO.builder()
            .id(order.getId())
            .version(order.getVersion() != null ? order.getVersion().intValue() : null)
            .createdAt(dateMapper.asOffsetDateTime(order.getCreatedAt()))
            .updatedAt(dateMapper.asOffsetDateTime(order.getUpdatedAt()))
            .orderStatus(order.getOrderStatus().toString())
            .customerReference(order.getCustomerReference())
            .build();

    try {
      log.debug("Posting to callback url");
      if (order.getOrderStatusCallbackUrl() != null) {
        restTemplate.postForObject(order.getOrderStatusCallbackUrl(), updateDTO, String.class);
      }
    } catch (Throwable throwable) {
      log.error("Error Preforming callback for order: " + order.getId(), throwable);
    }
  }
}
