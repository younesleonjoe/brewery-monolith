package com.younesleonjoe.brewerymonolith.service;

import com.younesleonjoe.brewerymonolith.dto.BeerOrderDTO;
import com.younesleonjoe.brewerymonolith.dto.BeerOrderPagedDTO;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerOrderService {

  BeerOrderPagedDTO listOrders(UUID customerId, Pageable pageable);

  BeerOrderDTO placeOrder(UUID customerId, BeerOrderDTO beerOrderDto);

  BeerOrderDTO getOrderById(UUID customerId, UUID orderId);

  void pickupOrder(UUID customerId, UUID orderId);
}
