package com.nimel.mymicroservices.beerorderservice.respository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.entity.Customer;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID>{
	
	Page<BeerOrder> findAllByCustomer(Customer customer,Pageable pageable);
	List<BeerOrder> findAllByOrderStatus(OrderStatusEnum orderStatusEnum);
	BeerOrder findOneById(UUID id);

}
