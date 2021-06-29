package com.nimel.mymicroservices.beerorderservice.listeners;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.nimel.mymicroservices.beerorderservice.config.JmsConfig;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderManager;
import com.nimel.mymicroservices.common.dtos.AllocateOrderResult;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Component
@AllArgsConstructor
@NoArgsConstructor
public class BeerAllocationResultListener {
	
	@Autowired
	private BeerOrderManager beerOrderManager;
	
	@Transactional
	@JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)
	public void listen(AllocateOrderResult result) {
		
		System.out.println("Result in ALLOCATE_ORDER_RESPONSE_QUEUE : " + result);
		System.out.println("Result dto in ALLOCATE_ORDER_RESPONSE_QUEUE : " + result.getBeerOrderDto());
		System.out.println("Result Alloc error in ALLOCATE_ORDER_RESPONSE_QUEUE : " + result.getAllocationError());
		System.out.println("Result Pending Inventory in ALLOCATE_ORDER_RESPONSE_QUEUE : " + result.getPendingInventory());
		System.out.println(beerOrderManager);
		
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
