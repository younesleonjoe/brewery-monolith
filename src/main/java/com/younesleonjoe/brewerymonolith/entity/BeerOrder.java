package com.younesleonjoe.brewerymonolith.entity;

import com.younesleonjoe.brewerymonolith.enums.OrderStatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BeerOrder extends BaseEntity {

  private String customerReference;
  @ManyToOne private Customer customer;

  @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL)
  @Fetch(FetchMode.JOIN)
  private Set<BeerOrderLine> beerOrderLines;

  private OrderStatusEnum orderStatus = OrderStatusEnum.NEW;
  private String orderStatusCallbackUrl;
}
