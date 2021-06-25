package com.nimel.mymicroservices.beerorderservice.events;

import com.nimel.mymicroservices.common.dtos.BeerOrderDto;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidateOrderRequest {
	
	private BeerOrderDto beerOrderDto;

}
