package com.nimel.mymicroservices.beerorderservice.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import com.nimel.mymicroservices.common.dtos.BeerOrderLineDto;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrderLine;


@Mapper(uses= {DateMapper.class})
//@DecoratedWith(BeerOrderLineMapperDecorator.class)
public interface BeerOrderLineMapper {
	
	BeerOrderLineDto toBeerOrderLineDto(BeerOrderLine beerOrderLine);
	BeerOrderLine toBeerOrderLine(BeerOrderLineDto beerOrderLineDto);
//	BeerOrderLineDto toBeerOrderLineDtoWithDetails(BeerOrderLine beerOrderLine);

}
