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
public class BeerInventory extends BaseEntity {

  @ManyToOne private Beer beer;
  private int quantityOnHand = 0;
}
