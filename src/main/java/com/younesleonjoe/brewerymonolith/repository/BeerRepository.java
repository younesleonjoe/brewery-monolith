package com.younesleonjoe.brewerymonolith.repository;

import com.younesleonjoe.brewerymonolith.entity.Beer;
import com.younesleonjoe.brewerymonolith.enums.BeerStyleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

  Page<Beer> findAllByName(String beerName, Pageable pageable);

  Page<Beer> findAllByStyle(BeerStyleEnum beerStyle, Pageable pageable);

  Page<Beer> findAllByNameAndStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageable);
}
