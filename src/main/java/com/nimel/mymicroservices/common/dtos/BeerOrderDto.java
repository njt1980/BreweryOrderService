package com.nimel.mymicroservices.common.dtos;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrderLine;
import com.nimel.mymicroservices.beerorderservice.entity.Customer;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
//@EqualsAndHashCode(callSuper = true)
//@AllArgsConstructor
public class BeerOrderDto extends BaseItem{
	
	@Builder
    public BeerOrderDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, UUID customerId, List<BeerOrderLineDto> beerOrderLines,
                        OrderStatusEnum orderStatus, String orderStatusCallbackUrl, String customerRef) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerId = customerId;
        this.beerOrderLines = beerOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
        this.customerRef = customerRef;
    }
	
	private UUID customerId;
	private String customerRef;
	private List<BeerOrderLineDto> beerOrderLines;
	private OrderStatusEnum orderStatus;
	private String orderStatusCallbackUrl;

}
