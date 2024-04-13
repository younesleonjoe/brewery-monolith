package com.younesleonjoe.brewerymonolith.mapper;

import com.younesleonjoe.brewerymonolith.dto.BeerOrderDTO;
import com.younesleonjoe.brewerymonolith.entity.BeerOrder;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

  BeerOrderDTO beerOrderToBeerOrderDTO(BeerOrder beerOrder);

  BeerOrder beerOrderDTOToBeerOrder(BeerOrderDTO beerOrderDTO);
}
