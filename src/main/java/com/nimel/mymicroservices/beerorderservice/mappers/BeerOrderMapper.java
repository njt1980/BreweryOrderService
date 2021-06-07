package com.nimel.mymicroservices.beerorderservice.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import com.nimel.mymicroservices.beerorderservice.dto.BeerOrderDto;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;

@Component
@Mapper(uses= {DateMapper.class,BeerOrderLineMapper.class})
public interface BeerOrderMapper {
	
	BeerOrderDto toBeerOrderDto(BeerOrder beerOrder);
	BeerOrder toBeerOrder(BeerOrderDto beerOrderDto);

}
