package com.nimel.mymicroservices.beerorderservice;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrderLine;
import com.nimel.mymicroservices.beerorderservice.entity.Customer;
import com.nimel.mymicroservices.beerorderservice.respository.CustomerRepository;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderManager;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderService;

@SpringBootApplication
public class BeerorderserviceApplication implements CommandLineRunner {
	
	@Autowired
	private BeerOrderService beerOrderService;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private BeerOrderManager beerOrderManager;
	

	public static void main(String[] args) {
		try {
	        SpringApplication.run(BeerorderserviceApplication.class, args);
	    } catch (Throwable e) {
	        if(e.getClass().getName().contains("SilentExitException")) {
	            System.out.println("Spring is restarting the main thread - See spring-boot-devtools");
	        } else {
	        	System.out.println("Application crashed!");
	        }
	    }
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("I am here");
		// TODO Auto-generated method stub
		Customer testCustomer = customerRepository.save(Customer.builder()
                .customerName("Test Customer")
                .build());
		
		BeerOrder beerOrder = createBeerOrder(testCustomer);
		
		beerOrderManager.newBeerOrder(beerOrder);
		
		
		
		
	}
	
	public BeerOrder createBeerOrder(Customer testCustomer){
		
        BeerOrder beerOrder = BeerOrder.builder()
                .customer(testCustomer)
                .build();

        Set<BeerOrderLine> lines = new HashSet<>();
        
        lines.add(BeerOrderLine.builder()
                .beerId(UUID.fromString("48789765-561e-4cb9-9359-aac5d849cf78"))
                .upc("0083783375213")
                .orderQuantity(1)
                .beerOrder(beerOrder)
                .build());

        beerOrder.setBeerOrderLines(lines);

        return beerOrder;
    }

}
