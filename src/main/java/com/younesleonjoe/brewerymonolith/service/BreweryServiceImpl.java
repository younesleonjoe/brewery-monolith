package com.younesleonjoe.brewerymonolith.service;

import com.younesleonjoe.brewerymonolith.entity.Brewery;
import com.younesleonjoe.brewerymonolith.repository.BreweryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BreweryServiceImpl implements BreweryService {

  private final BreweryRepository breweryRepository;

  @Override
  public List<Brewery> findAllBreweries() {
    return breweryRepository.findAll();
  }
}
