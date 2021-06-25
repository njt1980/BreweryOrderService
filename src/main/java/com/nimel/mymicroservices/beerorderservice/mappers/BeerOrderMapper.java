package com.nimel.mymicroservices.beerorderservice.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.nimel.mymicroservices.common.dtos.BeerOrderDto;
import com.nimel.mymicroservices.common.dtos.CustomerDto;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.entity.Customer;

//@Component
@Mapper(uses= {DateMapper.class,BeerOrderLineMapper.class})
@DecoratedWith(BeerOrderMapperDecorator.class)
public interface BeerOrderMapper {
	
	@Mapping(source="customer.id",target="customerId")
	BeerOrderDto toBeerOrderDto(BeerOrder beerOrder);
	
	BeerOrder toBeerOrder(BeerOrderDto beerOrderDto);
	
	@Mapping(source="customer.id",target="customerId")
	BeerOrderDto toBeerOrderDtoWithDetails(BeerOrder beerOrder);
	
	CustomerDto toCustomerDto(Customer customer);
	Customer toCustomer(CustomerDto customerDto);
	

}
