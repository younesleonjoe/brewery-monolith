package com.younesleonjoe.brewerymonolith.dto;

import com.younesleonjoe.brewerymonolith.enums.OrderStatusEnum;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderDTO extends BaseDTO {

  private UUID customerId;
  private String customerReference;
  private List<BeerOrderLineDTO> beerOrderLines;
  private OrderStatusEnum orderStatus;
  private String orderStatusCallbackUrl;

  @Builder
  public BeerOrderDTO(
      UUID id,
      Integer version,
      OffsetDateTime createdAt,
      OffsetDateTime updatedAt,
      UUID customerId,
      String customerReference,
      List<BeerOrderLineDTO> beerOrderLines,
      OrderStatusEnum orderStatus,
      String orderStatusCallbackUrl) {
    super(id, version, createdAt, updatedAt);
    this.customerId = customerId;
    this.customerReference = customerReference;
    this.beerOrderLines = beerOrderLines;
    this.orderStatus = orderStatus;
    this.orderStatusCallbackUrl = orderStatusCallbackUrl;
  }
}
