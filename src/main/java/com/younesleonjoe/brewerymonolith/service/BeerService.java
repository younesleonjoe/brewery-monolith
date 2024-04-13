package com.younesleonjoe.brewerymonolith.service;

import com.younesleonjoe.brewerymonolith.dto.BeerDTO;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

  Optional<BeerDTO> getBeerById(UUID id);
}
