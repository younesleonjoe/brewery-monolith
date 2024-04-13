package com.younesleonjoe.brewerymonolith.service;

import com.younesleonjoe.brewerymonolith.entity.Brewery;

import java.util.List;

public interface BreweryService {

  List<Brewery> findAllBreweries();
}
