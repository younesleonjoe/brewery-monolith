package com.younesleonjoe.brewerymonolith.repository;

import com.younesleonjoe.brewerymonolith.entity.Brewery;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreweryRepository extends JpaRepository<Brewery, UUID> {}
