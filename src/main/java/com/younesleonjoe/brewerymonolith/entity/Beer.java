package com.younesleonjoe.brewerymonolith.entity;

import com.younesleonjoe.brewerymonolith.enums.BeerStyleEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Beer extends BaseEntity {

  private String name;
  private BeerStyleEnum style;

  @Column(unique = true)
  private String upc;

  private Integer minOnHand;
  private Integer quantityToBrew;
  private BigDecimal price;

  @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL)
  @Fetch(FetchMode.JOIN)
  private Set<BeerInventory> beerInventory = new HashSet<>();

  @Builder
  public Beer(
      UUID id,
      Long version,
      Timestamp createdAt,
      Timestamp updatedAt,
      String name,
      BeerStyleEnum style,
      String upc,
      Integer minOnHand,
      Integer quantityToBrew,
      BigDecimal price,
      Set<BeerInventory> beerInventory) {
    super(id, version, createdAt, updatedAt);
    this.name = name;
    this.style = style;
    this.upc = upc;
    this.minOnHand = minOnHand;
    this.quantityToBrew = quantityToBrew;
    this.price = price;
    this.beerInventory = beerInventory;
  }
}
