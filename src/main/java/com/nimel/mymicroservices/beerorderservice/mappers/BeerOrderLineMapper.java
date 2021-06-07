package com.nimel.mymicroservices.beerorderservice.mappers;

import org.mapstruct.Mapper;

import com.nimel.mymicroservices.beerorderservice.dto.BeerOrderLineDto;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrderLine;


@Mapper(uses= {DateMapper.class})
public interface BeerOrderLineMapper {
	
	BeerOrderLineDto toBeerOrderLineDto(BeerOrderLine beerOrderLine);
	BeerOrderLine toBeerOrderLine(BeerOrderLineDto beerOrderLineDto);

}
