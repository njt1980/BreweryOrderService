package com.nimel.mymicroservices.beerorderservice.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nimel.mymicroservices.beerorderservice.dto.BeerDetails;
import com.nimel.mymicroservices.beerorderservice.dto.BeerOrderDto;
import com.nimel.mymicroservices.beerorderservice.dto.BeerOrderLineDto;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrderLine;
import com.nimel.mymicroservices.beerorderservice.services.BeerInfoService;

public abstract class BeerOrderMapperDecorator implements BeerOrderMapper{
	@Autowired
	private BeerOrderMapper beerOrderMapper;
	@Autowired
	private BeerInfoService beerInfoService;
	
//	BeerOrderDto toBeerOrderDto(BeerOrder beerOrder);
//	BeerOrder toBeerOrder(BeerOrderDto beerOrderDto);
//	BeerOrderDto toBeerOrderDtoWithDetails(BeerOrder beerOrder);
	
//	public BeerOrderDto toBeerOrderDto(BeerOrder beerOrder) {
//		return beerOrderMapper.toBeerOrderDto(beerOrder);
//	}
//	public BeerOrderDto toBeerOrderDto(BeerOrder beerOrder) {
//		BeerOrderDto beerOrderDto = beerOrderMapper.toBeerOrderDto(beerOrder);
//		List<BeerOrderLineDto> beerOrderLineDtos = beerOrderDto.getBeerOrderLines();
//		for(BeerOrderLineDto beerOrderLineDto:beerOrderLineDtos) {
//			String upc=beerOrderLineDto.getUpc();
//			BeerDetails beerDetails=beerInfoService.beerDetails(upc);
//			beerOrderLineDto.setBeerId(beerDetails.getBeerId());
//			beerOrderLineDto.setBeerName(beerDetails.getBeerName());
//		}
//		beerOrderDto.setBeerOrderLines(beerOrderLineDtos);
//		return beerOrderDto;
//	}
	
	public BeerOrder toBeerOrder(BeerOrderDto beerOrderDto) {
		return beerOrderMapper.toBeerOrder(beerOrderDto);
	}
	public BeerOrderDto toBeerOrderDtoWithDetails(BeerOrder beerOrder) {
		BeerOrderDto beerOrderDto = beerOrderMapper.toBeerOrderDto(beerOrder);
		List<BeerOrderLineDto> beerOrderLineDtos = beerOrderDto.getBeerOrderLines();
		for(BeerOrderLineDto beerOrderLineDto:beerOrderLineDtos) {
			String upc=beerOrderLineDto.getUpc();
			BeerDetails beerDetails=beerInfoService.beerDetails(upc);
			beerOrderLineDto.setBeerId(beerDetails.getBeerId());
			beerOrderLineDto.setBeerName(beerDetails.getBeerName());
		}
		beerOrderDto.setBeerOrderLines(beerOrderLineDtos);
		return beerOrderDto;
	}
	

}
