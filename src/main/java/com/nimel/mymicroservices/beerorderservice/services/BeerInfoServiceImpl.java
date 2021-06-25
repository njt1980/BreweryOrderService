package com.nimel.mymicroservices.beerorderservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nimel.mymicroservices.common.dtos.BeerDetails;
import com.nimel.mymicroservices.common.dtos.BeerDto;

@Service
public class BeerInfoServiceImpl implements BeerInfoService {
	@Autowired
	private RestTemplate restTemplate;
	
	private String beerSerLink="http://localhost:8080/api/v1/beerUpc/{upc}";

	@Override
	public BeerDetails beerDetails(String upc) {
		
		BeerDetails beerDetails = new BeerDetails();
		
		
//		List<Object> beerDetailsList = new ArrayList<>();
		
		ResponseEntity<BeerDto> responseEntity = 
		restTemplate.exchange(beerSerLink, HttpMethod.GET,null,new ParameterizedTypeReference<BeerDto>(){},(Object) upc);
		
		String beerName = Objects.requireNonNull(responseEntity.getBody().getBeerName());
		String beerStyle = Objects.requireNonNull(responseEntity.getBody().getBeerStyle());
		UUID beerId = Objects.requireNonNull(responseEntity.getBody().getId());
		beerDetails.setBeerId(beerId);
		beerDetails.setBeerName(beerName);
		beerDetails.setBeerStyle(beerStyle);
		return beerDetails;
		
		
	}

}
