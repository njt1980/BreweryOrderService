package com.nimel.mymicroservices.beerorderservice;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.management.loading.PrivateClassLoader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrderLine;
import com.nimel.mymicroservices.beerorderservice.entity.Customer;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;
import com.nimel.mymicroservices.beerorderservice.respository.BeerOrderRepository;
import com.nimel.mymicroservices.beerorderservice.respository.CustomerRepository;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderManager;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderManagerImpl;
import com.nimel.mymicroservices.common.dtos.BeerOrderLineDto;

import ch.qos.logback.core.status.Status;

@SpringBootTest
public class StateMachineTest {
	
	@Autowired
	private BeerOrderManager beerOrderManager;
	@Autowired
	private BeerOrderRepository beerOrderRepository;
	@Autowired
	private CustomerRepository customerRepository;
	Customer testCustomer;
	UUID beerId = UUID.randomUUID();
	
	
	
	@Test
	public void testProcess() {
		testCustomer = customerRepository.save(Customer.builder()
                .customerName("Test Customer")
                .build());
		
		BeerOrder beerOrder = createBeerOrder();
		
		beerOrderManager.newBeerOrder(beerOrder);
		System.out.println(beerOrder.getOrderStatus());
		assertNotEquals(beerOrder.getOrderStatus(),OrderStatusEnum.ALLOCATED);
		
		
	}
	
	public BeerOrder createBeerOrder(){
        BeerOrder beerOrder = BeerOrder.builder()
                .customer(testCustomer)
                .build();

        Set<BeerOrderLine> lines = new HashSet<>();
        lines.add(BeerOrderLine.builder()
                .beerId(beerId)
                .upc("12345")
                .orderQuantity(1)
                .beerOrder(beerOrder)
                .build());

        beerOrder.setBeerOrderLines(lines);

        return beerOrder;
    }

}
