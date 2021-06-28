package com.nimel.mymicroservices.beerorderservice.sm;

import java.nio.file.AccessDeniedException;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.stereotype.Component;

import com.nimel.mymicroservices.beerorderservice.entity.OrderEventEnum;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;


import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@NoArgsConstructor
@Slf4j
public class ErrorStateMachineListener extends StateMachineListenerAdapter<OrderStatusEnum, OrderEventEnum> {

	@Override
	public void eventNotAccepted(Message<OrderEventEnum> event) {
		System.out.println("Not Accepted" + event);
		
		
		
//	    throw new AccessDeniedException("Access is denied");
	}

	@Override
	public void stateMachineError(StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine, Exception exception) {
		log.error("Access is denied. Illegal state transition");
//	    throw new AccessDeniedException("Access is denied");
	}

	@Override
	public void stateContext(StateContext<OrderStatusEnum, OrderEventEnum> stateContext) {
		// TODO Auto-generated method stub
		
//		System.out.println("Context source state from Listener:" + stateContext.getSource());
//		System.out.println("Context state from Listener:" + stateContext.getExtendedState());
//		System.out.println("Context actions from Listener:" + stateContext.getSource().getExitActions());
//		System.out.println("Context stage from Listener:" + stateContext.getStage());
//		System.out.println("Context event from Listener:" + stateContext.getEvent());
//		System.out.println("Context exception from Listener :" + stateContext.getException());
//		stateContext.getException();
	}
	
	

}
