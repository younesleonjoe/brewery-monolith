package com.younesleonjoe.brewerymonolith.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Brewery extends BaseEntity {

  private String name;
}
