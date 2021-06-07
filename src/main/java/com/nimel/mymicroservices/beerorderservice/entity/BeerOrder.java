package com.nimel.mymicroservices.beerorderservice.entity;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class BeerOrder extends BaseEntity {
	
	private String customerRef;
	@ManyToOne
	private Customer customer;
	@OneToMany(mappedBy = "beerOrder")
	private Set<BeerOrderLine> beerOrderLines;
	private OrderStatusEnum orderStatus = OrderStatusEnum.NEW;
	private String orderStatusCallbackUrl;

}
