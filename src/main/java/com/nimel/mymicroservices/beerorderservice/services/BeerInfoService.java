package com.nimel.mymicroservices.beerorderservice.services;

import java.util.List;

import com.nimel.mymicroservices.common.dtos.BeerDetails;

public interface BeerInfoService {
	
	BeerDetails beerDetails(String upc);

}
