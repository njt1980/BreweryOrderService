package com.nimel.mymicroservices.beerorderservice.dto;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public class BeerOrderPagedList extends PageImpl<BeerOrderDto>{
	
	public BeerOrderPagedList(List<BeerOrderDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

	public BeerOrderPagedList(List<BeerOrderDto> content) {
		super(content);
		// TODO Auto-generated constructor stub
	}

}
