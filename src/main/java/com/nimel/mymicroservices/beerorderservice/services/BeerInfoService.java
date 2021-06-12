package com.nimel.mymicroservices.beerorderservice.services;

import java.util.List;

import com.nimel.mymicroservices.beerorderservice.dto.BeerDetails;

public interface BeerInfoService {
	
	BeerDetails beerDetails(String upc);

}
