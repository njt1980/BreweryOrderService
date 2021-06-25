package com.nimel.mymicroservices.beerorderservice.services;

import java.util.UUID;

import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.common.dtos.BeerOrderDto;

public interface BeerOrderManager {
	
	BeerOrder newBeerOrder(BeerOrder beerOrder);
	
	void processValidateResult(UUID orderId,Boolean isValid);
	
	void beerOrderAllocationPassed(BeerOrderDto beerOrderDto);
	
	void beerOrderAllocationPendingInventory(BeerOrderDto beerOrderDto);
	
	void beerOrderAllocationFailed(BeerOrderDto beerOrderDto);
	
	void beerOrderPickedUp(UUID id);

    void cancelOrder(UUID id);
	
	

}
