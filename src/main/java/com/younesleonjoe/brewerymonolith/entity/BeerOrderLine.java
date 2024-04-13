package com.younesleonjoe.brewerymonolith.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BeerOrderLine extends BaseEntity {

  @ManyToOne private BeerOrder beerOrder;
  @ManyToOne private Beer beer;

  private Integer orderQuantity = 0;
  private Integer quantityAllocated = 0;
}
