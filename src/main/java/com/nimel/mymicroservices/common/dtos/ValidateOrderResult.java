package com.nimel.mymicroservices.common.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateOrderResult {
	
	private UUID beerOrderId;
	private Boolean isValid;

}
