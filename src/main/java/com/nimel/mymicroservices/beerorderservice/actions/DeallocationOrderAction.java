package com.nimel.mymicroservices.beerorderservice.actions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.nimel.mymicroservices.beerorderservice.config.JmsConfig;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.entity.OrderEventEnum;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;
import com.nimel.mymicroservices.beerorderservice.mappers.BeerOrderMapper;
import com.nimel.mymicroservices.beerorderservice.respository.BeerOrderRepository;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderManagerImpl;
import com.nimel.mymicroservices.common.dtos.DeallocateOrderRequest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class DeallocationOrderAction implements Action<OrderStatusEnum, OrderEventEnum>{
	
	private BeerOrderRepository beerOrderRepository;
	private BeerOrderMapper beerOrderMapper;
	private JmsTemplate jmsTemplate;
	
	@Override
	public void execute(StateContext<OrderStatusEnum, OrderEventEnum> context) {
		String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            jmsTemplate.convertAndSend(JmsConfig.DEALLOCATE_ORDER_QUEUE,
                    DeallocateOrderRequest.builder()
                            .beerOrderDto(beerOrderMapper.toBeerOrderDto(beerOrder))
                            .build());
            log.debug("Sent Deallocation Request for order id: " + beerOrderId);
        }, () -> log.error("Beer Order Not Found!"));
    }
		// TODO Auto-generated method stub
		
	
	
	

}

