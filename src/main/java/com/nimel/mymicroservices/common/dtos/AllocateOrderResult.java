package com.nimel.mymicroservices.common.dtos;

import com.nimel.mymicroservices.common.dtos.BeerOrderDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllocateOrderResult {

	private BeerOrderDto beerOrderDto;
	private Boolean allocationError = false;
	private Boolean pendingInventory = false;
}
