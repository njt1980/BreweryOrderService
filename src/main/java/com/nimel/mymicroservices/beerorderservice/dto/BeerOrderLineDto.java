package com.nimel.mymicroservices.beerorderservice.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderLineDto extends BaseItem{
	
	private String upc;
	private String beerName;
	private UUID beerId;
	private int orderQuantity = 0;
	

}
