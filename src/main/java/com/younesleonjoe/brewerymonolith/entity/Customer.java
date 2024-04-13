package com.younesleonjoe.brewerymonolith.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer extends BaseEntity {

  private String name;

  @Column(columnDefinition = "varchar(36)")
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID apiKey;

  @OneToMany(mappedBy = "customer")
  private Set<BeerOrder> beerOrders;
  
  @Builder
  public Customer(UUID id, Long version, Timestamp createdAt, Timestamp updatedAt, String name, UUID apiKey, Set<BeerOrder> beerOrders) {
    super(id, version, createdAt, updatedAt);
    this.name = name;
    this.apiKey = apiKey;
    this.beerOrders = beerOrders;
  }
}
