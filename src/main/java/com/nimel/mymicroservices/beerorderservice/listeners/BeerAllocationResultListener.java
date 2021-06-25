package com.nimel.mymicroservices.beerorderservice.listeners;

import org.springframework.jms.annotation.JmsListener;

import com.nimel.mymicroservices.beerorderservice.config.JmsConfig;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderManager;
import com.nimel.mymicroservices.common.dtos.AllocateOrderResult;



public class BeerAllocationResultListener {
	
	private BeerOrderManager beerOrderManager;
	
	@JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)
	public void listen(AllocateOrderResult result) {
		
		if(!result.getAllocationError() && !result.getPendingInventory()){
            //allocated normally
            beerOrderManager.beerOrderAllocationPassed(result.getBeerOrderDto());
        } else if(!result.getAllocationError() && result.getPendingInventory()) {
            //pending inventory
            beerOrderManager.beerOrderAllocationPendingInventory(result.getBeerOrderDto());
        } else if(result.getAllocationError()){
            //allocation error
            beerOrderManager.beerOrderAllocationFailed(result.getBeerOrderDto());
        }
		
		
	}

}
