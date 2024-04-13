package com.younesleonjoe.brewerymonolith.dto;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderStatusUpdateDTO extends BaseDTO {

  private UUID orderId;
  private String customerReference;
  private String orderStatus;

  @Builder
  public OrderStatusUpdateDTO(
      UUID id,
      Integer version,
      OffsetDateTime createdAt,
      OffsetDateTime updatedAt,
      UUID orderId,
      String orderStatus,
      String customerReference) {
    super(id, version, createdAt, updatedAt);
    this.orderId = orderId;
    this.orderStatus = orderStatus;
    this.customerReference = customerReference;
  }
}
