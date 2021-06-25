package com.nimel.mymicroservices.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeallocateOrderRequest {

	private BeerOrderDto beerOrderDto;
}
