package com.nimel.mymicroservices.beerorderservice;

import java.util.List;

import org.h2.command.dml.BackupCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nimel.mymicroservices.common.dtos.BeerDetails;
import com.nimel.mymicroservices.beerorderservice.services.BeerInfoServiceImpl;

@SpringBootTest
class BeerorderserviceApplicationTests {
	@Autowired
	private BeerInfoServiceImpl beerInfoServiceImpl;
	private String upc="0631234200036";
	@Test
	void contextLoads() {
		System.out.println(upc);
		System.out.println(beerInfoServiceImpl);
		BeerDetails beerDetails = beerInfoServiceImpl.beerDetails(upc);
		System.out.println(beerDetails);
	
	}

}
