package com.younesleonjoe.brewerymonolith.service;

import com.younesleonjoe.brewerymonolith.dto.BeerOrderDTO;
import com.younesleonjoe.brewerymonolith.dto.BeerOrderPagedDTO;
import com.younesleonjoe.brewerymonolith.entity.BeerOrder;
import com.younesleonjoe.brewerymonolith.entity.Customer;
import com.younesleonjoe.brewerymonolith.enums.OrderStatusEnum;
import com.younesleonjoe.brewerymonolith.event.NewBeerOrderEvent;
import com.younesleonjoe.brewerymonolith.mapper.BeerOrderMapper;
import com.younesleonjoe.brewerymonolith.repository.BeerOrderRepository;
import com.younesleonjoe.brewerymonolith.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerOrderServiceImpl implements BeerOrderService {

  private final BeerOrderRepository beerOrderRepository;
  private final CustomerRepository customerRepository;
  private final BeerOrderMapper beerOrderMapper;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public BeerOrderPagedDTO listOrders(UUID customerId, Pageable pageable) {

    Optional<Customer> customerOptional = customerRepository.findById(customerId);

    if (customerOptional.isEmpty()) return null;

    Page<BeerOrder> beerOrderPage =
        beerOrderRepository.findAllByCustomer(customerOptional.get(), pageable);

    return new BeerOrderPagedDTO(
        beerOrderPage.stream()
            .map(beerOrderMapper::beerOrderToBeerOrderDTO)
            .collect(Collectors.toList()),
        PageRequest.of(
            beerOrderPage.getPageable().getPageNumber(), beerOrderPage.getPageable().getPageSize()),
        beerOrderPage.getTotalElements());
  }

  @Transactional
  @Override
  public BeerOrderDTO placeOrder(UUID customerId, BeerOrderDTO beerOrderDto) {

    Optional<Customer> customerOptional = customerRepository.findById(customerId);

    if (customerOptional.isEmpty()) {
      // TODO add exception type
      throw new RuntimeException("Customer Not Found");
    }

    BeerOrder beerOrder = beerOrderMapper.beerOrderDTOToBeerOrder(beerOrderDto);
    beerOrder.setId(null); // should not be set by outside client
    beerOrder.setCustomer(customerOptional.get());
    beerOrder.setOrderStatus(OrderStatusEnum.NEW);

    beerOrder.getBeerOrderLines().forEach(line -> line.setBeerOrder(beerOrder));

    BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

    log.debug("Saved Beer Order: " + beerOrder.getId());

    eventPublisher.publishEvent(new NewBeerOrderEvent(savedBeerOrder));

    return beerOrderMapper.beerOrderToBeerOrderDTO(savedBeerOrder);
  }

  @Override
  public BeerOrderDTO getOrderById(UUID customerId, UUID orderId) {

    return beerOrderMapper.beerOrderToBeerOrderDTO(getOrder(customerId, orderId));
  }

  @Override
  public void pickupOrder(UUID customerId, UUID orderId) {

    BeerOrder beerOrder = getOrder(customerId, orderId);
    beerOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);

    beerOrderRepository.save(beerOrder);
  }

  private BeerOrder getOrder(UUID customerId, UUID orderId) {

    Optional<Customer> customerOptional = customerRepository.findById(customerId);

    if (customerOptional.isEmpty()) throw new RuntimeException("Customer Not Found");

    Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(orderId);

    if (beerOrderOptional.isPresent()) {
      BeerOrder beerOrder = beerOrderOptional.get();

      if (beerOrder.getCustomer().getId().equals(customerId)) {
        return beerOrder;
      }
    }
    throw new RuntimeException("Beer Order Not Found");
  }
}
