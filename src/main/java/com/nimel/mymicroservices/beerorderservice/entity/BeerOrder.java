package com.nimel.mymicroservices.beerorderservice.entity;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	@OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL)
//	@OneToMany(mappedBy = "beerOrder")
//    @Fetch(FetchMode.JOIN)
	private Set<BeerOrderLine> beerOrderLines;
//	@Enumerated(EnumType.STRING)
	private OrderStatusEnum orderStatus = OrderStatusEnum.NEW;
	private String orderStatusCallbackUrl;

}
