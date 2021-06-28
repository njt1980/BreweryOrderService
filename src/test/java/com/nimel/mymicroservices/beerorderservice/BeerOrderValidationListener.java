package com.nimel.mymicroservices.beerorderservice;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.nimel.mymicroservices.beerorderservice.config.JmsConfig;
import com.nimel.mymicroservices.common.dtos.ValidateOrderRequest;
import com.nimel.mymicroservices.common.dtos.ValidateOrderResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

	private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void list(Message msg){
    	System.out.println("Listening to Validate Order Queue");
        boolean isValid = true;
        boolean sendResponse = true;

        ValidateOrderRequest request = (ValidateOrderRequest) msg.getPayload();

        //condition to fail validation
//        if (request.getBeerOrder().getCustomerRef() != null) {
//            if (request.getBeerOrder().getCustomerRef().equals("fail-validation")){
//                isValid = false;
//            } else if (request.getBeerOrder().getCustomerRef().equals("dont-validate")){
//                sendResponse = false;
//            }
//        }

        if (sendResponse) {
        	System.out.println("Listening to Validate Order Queue");
            jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                    ValidateOrderResult.builder()
                            .isValid(isValid)
                            .beerOrderId(request.getBeerOrderDto().getId())
                            .build());
        }
    }
}
