package com.younesleonjoe.brewerymonolith.mapper;

import com.younesleonjoe.brewerymonolith.dto.BeerDTO;
import com.younesleonjoe.brewerymonolith.entity.Beer;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

  BeerDTO beerToBeerDTO(Beer beer);

  Beer beerDTOToBeer(BeerDTO beerDTO);
}
