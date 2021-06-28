package com.nimel.mymicroservices.beerorderservice.listeners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.nimel.mymicroservices.beerorderservice.config.JmsConfig;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderManager;
import com.nimel.mymicroservices.common.dtos.ValidateOrderResult;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResultListener {
	@Autowired
	private BeerOrderManager beerOrderManager;
		
	@JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
	public void listen(ValidateOrderResult validateOrderResult) {
		
		final UUID orderId = validateOrderResult.getBeerOrderId();
		beerOrderManager.processValidateResult(orderId,validateOrderResult.getIsValid());
		
	}
	
	
	
	

}
