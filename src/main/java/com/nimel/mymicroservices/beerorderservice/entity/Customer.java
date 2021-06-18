package com.nimel.mymicroservices.beerorderservice.entity;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

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
	
	@Type(type="org.hibernate.type.UUIDCharType")
	@Column(length = 36, columnDefinition = "varchar(36)")
	private UUID apiKey;
	@OneToMany(mappedBy = "customer")
	private Set<BeerOrder> beerOrders;
	

}
