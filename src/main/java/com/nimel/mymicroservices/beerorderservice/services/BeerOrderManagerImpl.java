package com.nimel.mymicroservices.beerorderservice.services;


import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.transaction.Transactional;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
//import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.entity.OrderEventEnum;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;
import com.nimel.mymicroservices.beerorderservice.respository.BeerOrderRepository;
import com.nimel.mymicroservices.beerorderservice.sm.ErrorStateMachineListener;
import com.nimel.mymicroservices.beerorderservice.sm.StateMachineConfig;
import com.nimel.mymicroservices.common.dtos.BeerOrderDto;
import com.sun.xml.bind.v2.model.core.Adapter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
@Slf4j
public class BeerOrderManagerImpl implements BeerOrderManager{
	
	public static final String ORDER_ID_HEADER = "ORDER_ID_HEADER";
	private BeerOrderRepository beerOrderRepository;
	private BeerOrderStateChangeInterceptor beerOrderStateChangeInterceptor;
	private ErrorStateMachineListener errorStateMachineListener;
	
	private StateMachineFactory<OrderStatusEnum, OrderEventEnum> stateMachineFactory;

	
	@Transactional
	@Override
	public BeerOrder newBeerOrder(BeerOrder beerOrder) {
		
		beerOrder.setId(null);
		beerOrder.setOrderStatus(OrderStatusEnum.NEW);
		BeerOrder savedBeerOrder =  beerOrderRepository.saveAndFlush(beerOrder);
		sendBeerOrderEvent(beerOrder, OrderEventEnum.VALIDATE_ORDER);
		awaitForStatus(beerOrder.getId(), OrderStatusEnum.PENDING_VALIDATION);
		
//		try {
//			System.out.println("Yawnnn..");
//			Thread.sleep(10000);
//			System.out.println("Awake..");
//		}catch(InterruptedException e){
//			System.out.println("Inside sleep catch block");
//		}
		return savedBeerOrder;
		
	}
	
	
	public void processValidateResult(UUID orderId,Boolean isValid) {
		
		Optional<BeerOrder> beerOrderOptional =  beerOrderRepository.findById(orderId);
		System.out.println("Beer Order id from result queue :" + orderId.toString());
		System.out.println("Valid from result queue :" + isValid);
//		Optional.ofNullable(null)
		beerOrderOptional.ifPresentOrElse(beerOrder -> {
			if(isValid) {
				sendBeerOrderEvent(beerOrder, OrderEventEnum.VALIDATION_PASSED);
				awaitForStatus(orderId, OrderStatusEnum.VALIDATED);
                BeerOrder validatedOrder = beerOrderRepository.findById(orderId).get();
                System.out.println("ALL SET AND GOING TO SEND ALLOCATE_ORDER EVENT!!");
                sendBeerOrderEvent(validatedOrder, OrderEventEnum.ALLOCATE_ORDER);
            } else {
                sendBeerOrderEvent(beerOrder, OrderEventEnum.VALIDATION_FAILED);
            }
		}, () -> log.error("Order Not Found. Id: " + orderId));
	}
	
	@Override
    public void beerOrderAllocationPassed(BeerOrderDto beerOrderDto) {
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(beerOrderDto.getId());

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            sendBeerOrderEvent(beerOrder, OrderEventEnum.ALLOCATION_SUCCESS);
            awaitForStatus(beerOrder.getId(), OrderStatusEnum.ALLOCATED);
            System.out.println("BEER ALLOCATED*********");
            updateAllocatedQty(beerOrderDto);
        }, () -> log.error("Order Id Not Found: " + beerOrderDto.getId() ));
    }
	
	@Override
    public void beerOrderAllocationPendingInventory(BeerOrderDto beerOrderDto) {
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(beerOrderDto.getId());

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            sendBeerOrderEvent(beerOrder, OrderEventEnum.ALLOCATION_NO_INVENTORY);
            awaitForStatus(beerOrder.getId(), OrderStatusEnum.PENDING_INVENTORY);
            System.out.println("NO INVENTORY*********");
            updateAllocatedQty(beerOrderDto);
        }, () -> log.error("Order Id Not Found: " + beerOrderDto.getId() ));

    }
	
	private void sendBeerOrderEvent(BeerOrder beerOrder,OrderEventEnum orderEventEnum) {
		
		StateMachine<OrderStatusEnum,OrderEventEnum> sm = build(beerOrder);
		
		Message msg = MessageBuilder.withPayload(orderEventEnum)
				.setHeader(ORDER_ID_HEADER, beerOrder.getId().toString())
				.build();
		
		
		sm.addStateListener(errorStateMachineListener);
		System.out.println("Sending message via sendEvent..");
		sm.sendEvent(msg);
		
	}
	
	@Override
    public void beerOrderAllocationFailed(BeerOrderDto beerOrderDto) {
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(beerOrderDto.getId());

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            sendBeerOrderEvent(beerOrder, OrderEventEnum.ALLOCATION_FAILED);
        }, () -> log.error("Order Not Found. Id: " + beerOrderDto.getId()) );

    }
	
	@Override
    public void beerOrderPickedUp(UUID id) {
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(id);

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            //do process
            sendBeerOrderEvent(beerOrder, OrderEventEnum.BEER_ORDER_PICKED_UP);
        }, () -> log.error("Order Not Found. Id: " + id));
    }

    @Override
    public void cancelOrder(UUID id) {
        beerOrderRepository.findById(id).ifPresentOrElse(beerOrder -> {
            sendBeerOrderEvent(beerOrder, OrderEventEnum.CANCEL_ORDER);
        }, () -> log.error("Order Not Found. Id: " + id));
    }
	
	private void updateAllocatedQty(BeerOrderDto beerOrderDto) {
        Optional<BeerOrder> allocatedOrderOptional = beerOrderRepository.findById(beerOrderDto.getId());

        allocatedOrderOptional.ifPresentOrElse(allocatedOrder -> {
            allocatedOrder.getBeerOrderLines().forEach(beerOrderLine -> {
                beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
                    if(beerOrderLine.getId() .equals(beerOrderLineDto.getId())){
                        beerOrderLine.setQuantityAllocated(beerOrderLineDto.getQuantityAllocated());
                    }
                });
            });

            beerOrderRepository.saveAndFlush(allocatedOrder);
        }, () -> log.error("Order Not Found. Id: " + beerOrderDto.getId()));
    }
	
	private void awaitForStatus(UUID beerOrderId, OrderStatusEnum statusEnum) {

        AtomicBoolean found = new AtomicBoolean(false);
        AtomicInteger loopCount = new AtomicInteger(0);

        while (!found.get()) {
            if (loopCount.incrementAndGet() > 10) {
                found.set(true);
                log.debug("Loop Retries exceeded");
            }

            beerOrderRepository.findById(beerOrderId).ifPresentOrElse(beerOrder -> {
                if (beerOrder.getOrderStatus().equals(statusEnum)) {
                    found.set(true);
                    log.debug("Order Found");
                } else {
                    log.debug("Order Status Not Equal. Expected: " + statusEnum.name() + " Found: " + beerOrder.getOrderStatus().name());
                }
            }, () -> {
                log.debug("Order Id Not Found");
            });

            if (!found.get()) {
                try {
                    log.debug("Sleeping for retry");
                    Thread.sleep(100);
                } catch (Exception e) {
                    // do nothing
                }
            }
        }
    }
	
	
	private StateMachine<OrderStatusEnum, OrderEventEnum> build(BeerOrder beerOrder){

		StateMachine<OrderStatusEnum,OrderEventEnum> sm = stateMachineFactory.getStateMachine(beerOrder.getId());
//		System.out.println("Initial :" + sm.getId());
//		System.out.println("Initial State :" + sm.getState());
//		System.out.println("Got sm");
		sm.stop();
		
		System.out.println("sm stopped");
		sm.getStateMachineAccessor()
			.doWithAllRegions(sma -> {
				System.out.println("sm reset");
				sma.addStateMachineInterceptor(beerOrderStateChangeInterceptor);
				sma.resetStateMachine(new DefaultStateMachineContext<>(beerOrder.getOrderStatus(), null, null, null));
			});
		sm.start();
//		System.out.println("sm started");
//		System.out.println("Reset :" + sm.getId());
//		System.out.println("Reset State :" + sm.getState());
		
		return sm;
	}

}
