package com.younesleonjoe.brewerymonolith.bootstrap;

import com.younesleonjoe.brewerymonolith.entity.Beer;
import com.younesleonjoe.brewerymonolith.entity.Brewery;
import com.younesleonjoe.brewerymonolith.entity.Customer;
import com.younesleonjoe.brewerymonolith.enums.BeerStyleEnum;
import com.younesleonjoe.brewerymonolith.repository.BeerRepository;
import com.younesleonjoe.brewerymonolith.repository.BreweryRepository;
import com.younesleonjoe.brewerymonolith.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultBreweryLoader implements CommandLineRunner {

  public static final String TASTING_ROOM = "Tasting Room";
  public static final String BEER_1_UPC = "0631234200036";
  public static final String BEER_2_UPC = "0631234300019";
  public static final String BEER_3_UPC = "0083783375213";
  private final BreweryRepository breweryRepository;
  private final BeerRepository beerRepository;
  private final CustomerRepository customerRepository;

  @Override
  public void run(String... args) throws Exception {
    loadBreweryData();
    loadCustomerData();
  }

  private void loadCustomerData() {
    if (customerRepository.count() == 0) {
      customerRepository.save(
          Customer.builder().name(TASTING_ROOM).apiKey(UUID.randomUUID()).build());
    }
  }

  private void loadBreweryData() {
    if (breweryRepository.count() == 0) {
      breweryRepository.save(Brewery.builder().name("Cage Brewing").build());

      Beer mangoBobs =
          Beer.builder()
              .name("Mango Bobs")
              .style(BeerStyleEnum.IPA)
              .minOnHand(12)
              .quantityToBrew(200)
              .upc(BEER_1_UPC)
              .price(BigDecimal.valueOf(10))
              .build();

      beerRepository.save(mangoBobs);

      Beer galaxyCat =
          Beer.builder()
              .name("Galaxy Cat")
              .style(BeerStyleEnum.PALE_ALE)
              .minOnHand(12)
              .quantityToBrew(200)
              .upc(BEER_2_UPC)
              .price(BigDecimal.valueOf(15))
              .build();

      beerRepository.save(galaxyCat);

      Beer pinball =
          Beer.builder()
              .name("Pinball Porter")
              .style(BeerStyleEnum.PORTER)
              .minOnHand(12)
              .quantityToBrew(200)
              .upc(BEER_3_UPC)
              .price(BigDecimal.valueOf(20))
              .build();

      beerRepository.save(pinball);
    }
  }
}
