package com.younesleonjoe.brewerymonolith.mapper;

import com.younesleonjoe.brewerymonolith.dto.BeerOrderLineDTO;
import com.younesleonjoe.brewerymonolith.entity.BeerOrderLine;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerOrderLineMapperDecorator.class)
public interface BeerOrderLineMapper {

  BeerOrderLineDTO beerOrderLineToBeerOrderLineDTO(BeerOrderLine orderLine);

  BeerOrderLine beerOrderLineDTOToBeerOrderLine(BeerOrderLineDTO dto);
}
