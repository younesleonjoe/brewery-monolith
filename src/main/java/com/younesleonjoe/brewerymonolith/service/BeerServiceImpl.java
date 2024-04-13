package com.younesleonjoe.brewerymonolith.service;

import com.younesleonjoe.brewerymonolith.dto.BeerDTO;
import java.util.Optional;
import java.util.UUID;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// @ConfigurationProperties(prefix = "younesleonjoe.brewerymonolith", ignoreUnknownFields = false)
@Service
public class BeerServiceImpl implements BeerService {

  private final String BEER_PATH_V1 = "/api/v1/beers";
  private final RestTemplate restTemplate;
  @Setter private String beerServiceHost;

  public BeerServiceImpl(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Override
  public Optional<BeerDTO> getBeerById(UUID id) {
    return Optional.ofNullable(
        restTemplate.getForObject(beerServiceHost + BEER_PATH_V1 + id, BeerDTO.class));
  }
}
