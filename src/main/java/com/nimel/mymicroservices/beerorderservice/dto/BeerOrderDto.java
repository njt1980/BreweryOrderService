package com.nimel.mymicroservices.beerorderservice.dto;

import java.util.Set;
import java.util.UUID;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrderLine;
import com.nimel.mymicroservices.beerorderservice.entity.Customer;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderDto extends BaseItem{
	
	private UUID customerId;
	private Customer customer;
	private Set<BeerOrderLineDto> beerOrderLines;
	private OrderStatusEnum orderStatus;
	private String orderStatusCallbackUrl;

}
