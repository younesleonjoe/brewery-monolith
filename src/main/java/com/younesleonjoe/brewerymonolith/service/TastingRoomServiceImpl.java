package com.younesleonjoe.brewerymonolith.service;

import com.younesleonjoe.brewerymonolith.bootstrap.DefaultBreweryLoader;
import com.younesleonjoe.brewerymonolith.dto.BeerOrderDTO;
import com.younesleonjoe.brewerymonolith.dto.BeerOrderLineDTO;
import com.younesleonjoe.brewerymonolith.entity.Beer;
import com.younesleonjoe.brewerymonolith.entity.Customer;
import com.younesleonjoe.brewerymonolith.repository.BeerOrderRepository;
import com.younesleonjoe.brewerymonolith.repository.BeerRepository;
import com.younesleonjoe.brewerymonolith.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TastingRoomServiceImpl implements TastingRoomService {

  private final CustomerRepository customerRepository;
  private final BeerOrderService beerOrderService;

  private final BeerRepository beerRepository;
  private final BeerOrderRepository beerOrderRepository;

  @Transactional
  @Scheduled(fixedRate = 2000) // runs every 2 seconds
  @Override
  public void placeTastingRoomOrder() {

    List<Customer> customerList =
        customerRepository.findAllByNameLike(DefaultBreweryLoader.TASTING_ROOM);

    if (customerList.size() != 1)
      log.error(
          "TastingRoomServiceImpl.placeTastingRoomOrder failed. Too many or too "
              + "few tasting room "
              + "customers found");

    doPlaceOrder(customerList.get(0));
  }

  private void doPlaceOrder(Customer customer) {

    Beer beerToOrder = getRandomBeer();

    BeerOrderLineDTO beerOrderLine =
        BeerOrderLineDTO.builder()
            .beerId(beerToOrder.getId())
            .orderQuantity(new Random().nextInt(6)) // TODO externalize value to property
            .build();

    List<BeerOrderLineDTO> beerOrderLineSet = new ArrayList<>();
    beerOrderLineSet.add(beerOrderLine);

    BeerOrderDTO beerOrder =
        BeerOrderDTO.builder()
            .customerId(customer.getId())
            .customerReference(UUID.randomUUID().toString())
            // .orderStatusCallbackUrl("http://localhost:8080/beerorder") // TODO update
            .beerOrderLines(beerOrderLineSet)
            .build();

    BeerOrderDTO savedOrder = beerOrderService.placeOrder(customer.getId(), beerOrder);

    log.debug("TastingRoomServiceImpl saved tasting room order: " + savedOrder.getId());
  }

  private Beer getRandomBeer() {

    List<Beer> beers = beerRepository.findAll();

    return beers.get(new Random().nextInt(beers.size()));
  }
}
