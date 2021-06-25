package com.nimel.mymicroservices.common.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderLineDto extends BaseItem {

    @Builder
    public BeerOrderLineDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                            String upc, String beerName, UUID beerId, Integer orderQuantity) {
        super(id, version, createdDate, lastModifiedDate);
        this.upc = upc;
        this.beerName = beerName;
        this.beerId = beerId;
        this.orderQuantity = orderQuantity;
    }

    private String upc;
    private String beerName;
    private UUID beerId;
    private Integer orderQuantity = 0;
    private Integer quantityAllocated;
}

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class BeerOrderLineDto extends BaseItem{
//	
//	private String upc;
//	private String beerName;
//	private UUID beerId;
//	private int orderQuantity = 0;
//	
//
//}
