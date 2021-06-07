package com.nimel.mymicroservices.beerorderservice.entity;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

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
public class Customer extends BaseEntity{
	
	private String customerName;
	private UUID apiKey;
	@OneToMany(mappedBy = "customer")
	private Set<BeerOrder> beerOrders;
	

}
