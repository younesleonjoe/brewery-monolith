package com.younesleonjoe.brewerymonolith.repository;

import com.younesleonjoe.brewerymonolith.entity.BeerOrderLine;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BeerOrderLineRepository extends PagingAndSortingRepository<BeerOrderLine, UUID> {}
