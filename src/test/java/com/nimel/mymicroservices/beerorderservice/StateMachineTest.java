package com.nimel.mymicroservices.beerorderservice;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.management.loading.PrivateClassLoader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrderLine;
import com.nimel.mymicroservices.beerorderservice.entity.Customer;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;
import com.nimel.mymicroservices.beerorderservice.respository.BeerOrderRepository;
import com.nimel.mymicroservices.beerorderservice.respository.CustomerRepository;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderManager;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderManagerImpl;
import com.nimel.mymicroservices.common.dtos.BeerDto;
import com.nimel.mymicroservices.common.dtos.BeerOrderDto;
import com.nimel.mymicroservices.common.dtos.BeerOrderLineDto;

import ch.qos.logback.core.status.Status;
@ExtendWith(WireMockExtension.class)
//@SpringBootTest
public class StateMachineTest {
	
	@Autowired
	private BeerOrderManager beerOrderManager;
	@Autowired
	private BeerOrderRepository beerOrderRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
    ObjectMapper objectMapper;

    @Autowired
    WireMockServer wireMockServer;
	Customer testCustomer;
	UUID beerId = UUID.randomUUID();
	
	 @TestConfiguration
	    static class RestTemplateBuilderProvider {
	        @Bean(destroyMethod = "stop")
	        public WireMockServer wireMockServer(){
	            WireMockServer server = with(wireMockConfig().port(8083));
	            server.start();
	            return server;
	        }
	    }
	
	@Test
	public void testNewToAllocated() throws JsonProcessingException, InterruptedException {
		
		testCustomer = customerRepository.save(Customer.builder()
                .customerName("Test Customer")
                .build());
		
		BeerDto beerDto = BeerDto.builder().id(beerId).upc("12345").build();
		
		wireMockServer.stubFor(get("http://localhost:8083/api/v1/beerUpc/" + "12345")
		        .willReturn(okJson(objectMapper.writeValueAsString(beerDto))));
		
		BeerOrder beerOrder = createBeerOrder();
		
		BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);
		
		Thread.sleep(8000);
		
		assertNotNull(savedBeerOrder);
		System.out.println(savedBeerOrder.getOrderStatus());
		System.out.println(savedBeerOrder);
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
