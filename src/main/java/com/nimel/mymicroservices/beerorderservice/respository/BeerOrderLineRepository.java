package com.nimel.mymicroservices.beerorderservice.respository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.nimel.mymicroservices.beerorderservice.entity.BeerOrderLine;

public interface BeerOrderLineRepository extends PagingAndSortingRepository<BeerOrderLine, UUID>{

}
