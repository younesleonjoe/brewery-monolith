package com.younesleonjoe.brewerymonolith.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderLineDTO extends BaseDTO {
  
  private UUID beerId;
  private Integer orderQuantity = 0;
  @Builder
  public BeerOrderLineDTO(UUID id, Integer version, OffsetDateTime createdAt, OffsetDateTime updatedAt, UUID beerId, Integer orderQuantity) {
    super(id, version, createdAt, updatedAt);
    this.beerId = beerId;
    this.orderQuantity = orderQuantity;
  }
}
