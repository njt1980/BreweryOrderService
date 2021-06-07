package com.nimel.mymicroservices.beerorderservice.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseItem {
	
	@Null
	private UUID id;
	@Null
	private Long version;
	@Null
	private OffsetDateTime createdDate;
	@Null
	private OffsetDateTime lastModifiedDate;
	

}
