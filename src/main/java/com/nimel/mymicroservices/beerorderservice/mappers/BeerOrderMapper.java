package com.nimel.mymicroservices.beerorderservice.mappers;

import org.mapstruct.Mapper;

import com.nimel.mymicroservices.beerorderservice.dto.BeerOrderDto;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;

@Mapper(uses= {DateMapper.class})
public interface BeerOrderMapper {
	
	BeerOrderDto toBeerOrderDto(BeerOrder beerOrder);
	BeerOrder toBeerOrder(BeerOrderDto beerOrderDto);

}
