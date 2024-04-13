package com.younesleonjoe.brewerymonolith.dto;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerDTO extends BaseDTO {

  private String customerName;

  @Builder
  public CustomerDTO(
      UUID id,
      Integer version,
      OffsetDateTime createdAt,
      OffsetDateTime updatedAt,
      String customerName) {
    super(id, version, createdAt, updatedAt);
    this.customerName = customerName;
  }
}
