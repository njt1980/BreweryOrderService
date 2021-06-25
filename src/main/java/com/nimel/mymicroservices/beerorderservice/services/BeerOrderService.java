package com.nimel.mymicroservices.beerorderservice.services;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.nimel.mymicroservices.common.dtos.BeerOrderDto;
import com.nimel.mymicroservices.common.dtos.BeerOrderPagedList;

public interface BeerOrderService {
	
	BeerOrderPagedList listOrders(UUID customerId,Pageable pageable);
	
	BeerOrderDto placeOrder(UUID customerId,BeerOrderDto beerOrderDto);
	
	BeerOrderDto getOrderById(UUID customerId,UUID orderId);
	
	void pickupOrder(UUID customerId,UUID orderId);

}
