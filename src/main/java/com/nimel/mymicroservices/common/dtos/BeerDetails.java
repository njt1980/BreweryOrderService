package com.nimel.mymicroservices.common.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeerDetails {
	
	private String beerName;
	private String beerStyle;
	private UUID beerId;
	

}
