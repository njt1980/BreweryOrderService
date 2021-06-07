package com.nimel.mymicroservices.beerorderservice.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BeerOrderLine extends BaseEntity{
	
	@ManyToOne
	private BeerOrder beerOrder;
	private UUID beerId;
	private int orderQuantity = 0;
	private int quantityAllocated = 0;

}
