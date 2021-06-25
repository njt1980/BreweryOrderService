package com.nimel.mymicroservices.common.dtos;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto implements Serializable{
	
	@Null
	private UUID id;
	@Null
	private Long version;
	@Null
	private OffsetDateTime createdDate;
	@Null
	private OffsetDateTime lastModifiedDate;
	
	@NotBlank
	private String beerName;
	
	@NotNull
	private String beerStyle;
	@NotNull
	private String upc;
	@Positive
	private int price;
	
	private int quantityOnHand;
	
	private int minOnHand;
	private int quantityToBrew;

}
