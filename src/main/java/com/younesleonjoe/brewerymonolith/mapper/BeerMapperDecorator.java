package com.younesleonjoe.brewerymonolith.mapper;

import com.younesleonjoe.brewerymonolith.dto.BeerDTO;
import com.younesleonjoe.brewerymonolith.entity.Beer;
import com.younesleonjoe.brewerymonolith.entity.BeerInventory;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;

@Setter
public abstract class BeerMapperDecorator implements BeerMapper {

  @Qualifier("delegate")
  private BeerMapper delegate;

  @Override
  public BeerDTO beerToBeerDTO(Beer beer) {

    BeerDTO beerDTO = delegate.beerToBeerDTO(beer);

    if (beer.getBeerInventory() != null && !beer.getBeerInventory().isEmpty()) {

      beerDTO.setQuantityOnHand(
          beer.getBeerInventory().stream()
              .map(BeerInventory::getQuantityOnHand)
              .reduce(0, Integer::sum));
    }

    return beerDTO;
  }
}
