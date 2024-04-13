package com.younesleonjoe.brewerymonolith.repository;

import com.younesleonjoe.brewerymonolith.entity.BeerOrder;
import com.younesleonjoe.brewerymonolith.entity.Customer;
import com.younesleonjoe.brewerymonolith.enums.OrderStatusEnum;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {

  Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);

  List<BeerOrder> findAllByOrderStatus(OrderStatusEnum orderStatus);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  BeerOrder findOneById(UUID id);
}
