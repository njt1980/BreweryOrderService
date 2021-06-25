package com.nimel.mymicroservices.beerorderservice.actions;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.nimel.mymicroservices.beerorderservice.entity.OrderEventEnum;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderManagerImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ValidationFailureAction implements Action<OrderStatusEnum, OrderEventEnum> {
	
	 @Override
	    public void execute(StateContext<OrderStatusEnum, OrderEventEnum> context) {
	        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
	        log.error("Compensating Transaction.... Validation Failed: " + beerOrderId);
	    }

}
