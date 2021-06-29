package com.nimel.mymicroservices.beerorderservice.services;


import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.entity.OrderEventEnum;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;
import com.nimel.mymicroservices.beerorderservice.respository.BeerOrderRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@AllArgsConstructor
public class BeerOrderStateChangeInterceptor extends StateMachineInterceptorAdapter<OrderStatusEnum, OrderEventEnum>{
	
	private BeerOrderRepository beerOrderRepository;
	
	@Transactional
	@Override
	public void preStateChange(State<OrderStatusEnum, OrderEventEnum> state, Message<OrderEventEnum> message,
			Transition<OrderStatusEnum, OrderEventEnum> transition,
			StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine,
			StateMachine<OrderStatusEnum, OrderEventEnum> rootStateMachine) {
		// TODO Auto-generated method stub
//		super.preStateChange(state, message, transition, stateMachine, rootStateMachine);
		System.out.println("Pre State Change..");
		
		Optional.ofNullable(message)
        .flatMap(msg -> Optional.ofNullable((String) msg.getHeaders().getOrDefault(BeerOrderManagerImpl.ORDER_ID_HEADER, " ")))
        .ifPresent(orderId -> {
            log.debug("Saving state for order id: " + orderId + " Status: " + state.getId());
            System.out.println("Saving state for order id: " + orderId + " Status: " + state.getId());
            BeerOrder beerOrder = beerOrderRepository.getById(UUID.fromString(orderId));
            beerOrder.setOrderStatus(state.getId());
            beerOrderRepository.saveAndFlush(beerOrder);
        });	
	}
	
//	@Transactional
//	@Override
//	public void preStateChange(State<OrderStatusEnum, OrderEventEnum> state, Message<OrderEventEnum> message,
//			Transition<OrderStatusEnum, OrderEventEnum> transition, StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine) {
//		
//		System.out.println("Pre State Change..");
//		
//		Optional.ofNullable(message)
//        .flatMap(msg -> Optional.ofNullable((String) msg.getHeaders().getOrDefault(BeerOrderManagerImpl.ORDER_ID_HEADER, " ")))
//        .ifPresent(orderId -> {
//            log.debug("Saving state for order id: " + orderId + " Status: " + state.getId());
//            System.out.println("Saving state for order id: " + orderId + " Status: " + state.getId());
//            BeerOrder beerOrder = beerOrderRepository.getById(UUID.fromString(orderId));
//            beerOrder.setOrderStatus(state.getId());
//            beerOrderRepository.saveAndFlush(beerOrder);
//        });
//		
//	}

	

}
