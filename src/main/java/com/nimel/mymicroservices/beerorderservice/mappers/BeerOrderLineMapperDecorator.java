package com.nimel.mymicroservices.beerorderservice.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nimel.mymicroservices.common.dtos.BeerDetails;
import com.nimel.mymicroservices.common.dtos.BeerOrderLineDto;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrderLine;
import com.nimel.mymicroservices.beerorderservice.services.BeerInfoService;

public class BeerOrderLineMapperDecorator {
	@Autowired
	private BeerOrderLineMapper beerOrderLineMapper;
	@Autowired
	private BeerInfoService beerInfoService;
	

//	BeerOrderLineDto toBeerOrderLineDtoWithDetails(BeerOrderLine beerOrderLine);
	
	public BeerOrderLineDto toBeerOrderLineDto(BeerOrderLine beerOrderLine) {
		return beerOrderLineMapper.toBeerOrderLineDto(beerOrderLine);
	}
	public BeerOrderLine toBeerOrderLine(BeerOrderLineDto beerOrderLineDto) {
		return beerOrderLineMapper.toBeerOrderLine(beerOrderLineDto);
	}
//	@Override
//	public BeerOrderLineDto toBeerOrderLineDtoWithDetails(BeerOrderLine beerOrderLine) {
//		BeerOrderLineDto beerOrderLineDto = beerOrderLineMapper.toBeerOrderLineDto(beerOrderLine);
//		String upc=beerOrderLineDto.getUpc();
//		BeerDetails beerDetails=beerInfoService.beerDetails(upc);
//		beerOrderLineDto.setBeerId(beerDetails.getBeerId());
//		beerOrderLineDto.setBeerName(beerDetails.getBeerName());
//		return beerOrderLineDto;
//	}
	

}
