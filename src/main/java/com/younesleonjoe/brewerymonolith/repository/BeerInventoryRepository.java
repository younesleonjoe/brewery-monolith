package com.younesleonjoe.brewerymonolith.repository;

import com.younesleonjoe.brewerymonolith.entity.Beer;
import com.younesleonjoe.brewerymonolith.entity.BeerInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerInventoryRepository extends JpaRepository<BeerInventory, UUID> {

  List<BeerInventory> findAllByBeer(Beer beer);
}
