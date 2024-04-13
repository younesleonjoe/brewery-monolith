package com.younesleonjoe.brewerymonolith.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.younesleonjoe.brewerymonolith.enums.BeerStyleEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerDTO extends BaseDTO {

  private String beerName;
  private BeerStyleEnum beerStyle;
  private String upc;
  private Integer quantityOnHand;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private BigDecimal price;

  @Builder
  public BeerDTO(
      UUID id,
      Integer version,
      OffsetDateTime createdAt,
      OffsetDateTime updatedAt,
      String beerName,
      BeerStyleEnum beerStyle,
      String upc,
      Integer quantityOnHand,
      BigDecimal price) {
    super(id, version, createdAt, updatedAt);
    this.beerName = beerName;
    this.beerStyle = beerStyle;
    this.upc = upc;
    this.quantityOnHand = quantityOnHand;
    this.price = price;
  }
}
