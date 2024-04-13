package com.younesleonjoe.brewerymonolith.dto;

import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class BeerOrderPagedDTO extends PageImpl<BeerOrderDTO> {

  public BeerOrderPagedDTO(List<BeerOrderDTO> content, Pageable pageable, long total) {
    super(content, pageable, total);
  }

  public BeerOrderPagedDTO(List<BeerOrderDTO> content) {
    super(content);
  }
}
