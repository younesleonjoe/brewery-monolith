package com.younesleonjoe.brewerymonolith.mapper;

import com.younesleonjoe.brewerymonolith.dto.BeerOrderLineDTO;
import com.younesleonjoe.brewerymonolith.entity.BeerOrderLine;
import com.younesleonjoe.brewerymonolith.repository.BeerRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {

  private BeerRepository beerRepository;

  private BeerOrderLineMapper delegate;

  @Autowired
  public void setBeerRepository(BeerRepository beerRepository) {
    this.beerRepository = beerRepository;
  }

  @Autowired
  @Qualifier("delegate")
  public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
    this.delegate = beerOrderLineMapper;
  }

  @Override
  public BeerOrderLineDTO beerOrderLineToBeerOrderLineDTO(BeerOrderLine beerOrderLine) {
    BeerOrderLineDTO beerOrderLineDto = delegate.beerOrderLineToBeerOrderLineDTO(beerOrderLine);
    beerOrderLineDto.setBeerId(beerOrderLine.getBeer().getId());
    return beerOrderLineDto;
  }

  @Override
  public BeerOrderLine beerOrderLineDTOToBeerOrderLine(BeerOrderLineDTO beerOrderLineDTO) {
    BeerOrderLine beerOrderLine = delegate.beerOrderLineDTOToBeerOrderLine(beerOrderLineDTO);
    beerOrderLine.setBeer(beerRepository.getReferenceById(beerOrderLineDTO.getBeerId()));
    beerOrderLine.setQuantityAllocated(0);
    return beerOrderLine;
  }
}
