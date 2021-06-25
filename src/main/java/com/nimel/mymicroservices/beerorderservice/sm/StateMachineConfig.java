package com.nimel.mymicroservices.beerorderservice.sm;

import java.util.EnumSet;

import javax.management.loading.PrivateClassLoader;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import com.nimel.mymicroservices.beerorderservice.entity.OrderEventEnum;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;
import com.nimel.mymicroservices.beerorderservice.events.ValidateOrderRequest;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableStateMachineFactory
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<OrderStatusEnum, OrderEventEnum> {
	
//	private ValidateOrderRequest validateOrderRequest;
	private Action<OrderStatusEnum, OrderEventEnum> validateOrderActions;
	private Action<OrderStatusEnum, OrderEventEnum> allocateOrderAction;
	private Action<OrderStatusEnum, OrderEventEnum> deallocateOrderAction;
	private Action<OrderStatusEnum, OrderEventEnum> validationFailureAction;
	private Action<OrderStatusEnum, OrderEventEnum> allocationFailureAction;
	@Override
	public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderEventEnum> states) throws Exception {
		states.withStates()
		.initial(OrderStatusEnum.NEW)
		.states(EnumSet.allOf(OrderStatusEnum.class))
		.end(OrderStatusEnum.PICKED_UP)
		.end(OrderStatusEnum.VALIDATION_EXCEPTION)
		.end(OrderStatusEnum.ALLOCATION_ERROR)
		.end(OrderStatusEnum.DELIVERED)
		.end(OrderStatusEnum.DELIVERY_EXCEPTION)
		.end(OrderStatusEnum.CANCELLED);
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderEventEnum> transitions)
			throws Exception {
		
		transitions.withExternal().source(OrderStatusEnum.NEW).target(OrderStatusEnum.PENDING_VALIDATION).event(OrderEventEnum.VALIDATE_ORDER)
		.action(validateOrderActions)
		.and()
		.withExternal().source(OrderStatusEnum.PENDING_VALIDATION).target(OrderStatusEnum.VALIDATED).event(OrderEventEnum.VALIDATION_PASSED)
		.and()
		.withExternal().source(OrderStatusEnum.PENDING_VALIDATION).target(OrderStatusEnum.VALIDATION_EXCEPTION).event(OrderEventEnum.VALIDATION_FAILED)
		.action(validationFailureAction)
		.and()
		.withExternal().source(OrderStatusEnum.VALIDATED).target(OrderStatusEnum.PENDING_ALLOCATION).event(OrderEventEnum.ALLOCATE_ORDER)
		.action(allocateOrderAction)
		.and()
		.withExternal().source(OrderStatusEnum.PENDING_ALLOCATION).target(OrderStatusEnum.ALLOCATED).event(OrderEventEnum.ALLOCATION_SUCCESS)
		.and()
		.withExternal().source(OrderStatusEnum.PENDING_ALLOCATION).target(OrderStatusEnum.ALLOCATION_ERROR).event(OrderEventEnum.ALLOCATION_FAILED)
		.action(allocationFailureAction)
		.and()
		.withExternal().source(OrderStatusEnum.PENDING_ALLOCATION).target(OrderStatusEnum.PENDING_INVENTORY).event(OrderEventEnum.ALLOCATION_NO_INVENTORY)
		.and()
		.withExternal().source(OrderStatusEnum.ALLOCATED).target(OrderStatusEnum.PICKED_UP).event(OrderEventEnum.BEER_ORDER_PICKED_UP)
		.and()
		.withExternal().source(OrderStatusEnum.ALLOCATED).target(OrderStatusEnum.CANCELLED).event(OrderEventEnum.CANCEL_ORDER)
		.and()
		.withExternal().source(OrderStatusEnum.NEW).target(OrderStatusEnum.CANCELLED).event(OrderEventEnum.CANCEL_ORDER)
		.and()
		.withExternal().source(OrderStatusEnum.VALIDATED).target(OrderStatusEnum.CANCELLED).event(OrderEventEnum.CANCEL_ORDER)
		.action(deallocateOrderAction);

	}
	
	private Action<OrderStatusEnum, OrderEventEnum> allocateInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	private Action<OrderStatusEnum, OrderEventEnum> validateOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void configure(StateMachineConfigurationConfigurer<OrderStatusEnum, OrderEventEnum> config) throws Exception {
		
		StateMachineListenerAdapter<OrderStatusEnum, OrderEventEnum> adapter = new StateMachineListenerAdapter<OrderStatusEnum, OrderEventEnum> () {
			@Override
			public void stateChanged(State<OrderStatusEnum, OrderEventEnum> from,State<OrderStatusEnum, OrderEventEnum>  to) {
				log.info(String.format("StateChanged(from:%s,to:%s)", from, to));
			}
		};
		config.withConfiguration().listener(adapter);
	}

	
	
	

}
