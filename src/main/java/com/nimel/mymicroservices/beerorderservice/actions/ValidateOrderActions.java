package com.nimel.mymicroservices.beerorderservice.actions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import com.nimel.mymicroservices.beerorderservice.config.JmsConfig;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.entity.OrderEventEnum;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;
import com.nimel.mymicroservices.beerorderservice.events.ValidateOrderRequest;
import com.nimel.mymicroservices.beerorderservice.mappers.BeerOrderMapper;
import com.nimel.mymicroservices.beerorderservice.respository.BeerOrderRepository;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderManagerImpl;

import jdk.internal.org.jline.utils.Log;

public class ValidateOrderActions implements Action<OrderStatusEnum, OrderEventEnum>{
	
	private BeerOrderRepository beerOrderRepository;
	private BeerOrderMapper beerOrderMapper;
	private JmsTemplate jmsTemplate;

	@Override
	public void execute(StateContext<OrderStatusEnum, OrderEventEnum> context) {
		// TODO Auto-generated method stub
		String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
		Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));
		
		beerOrderOptional.ifPresentOrElse(beerOrder -> {
			jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE,ValidateOrderRequest.builder()
					.beerOrderDto(beerOrderMapper.toBeerOrderDto(beerOrder))
					.build());
		},() -> Log.error("Order not found: " + beerOrderId));
		System.out.println("Validation request sent to queue for order id" + beerOrderId);
	}

}
