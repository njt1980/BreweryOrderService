package com.nimel.mymicroservices.beerorderservice.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nimel.mymicroservices.beerorderservice.dto.BeerOrderLineDto;
import com.nimel.mymicroservices.beerorderservice.dto.BeerOrderDto;
import com.nimel.mymicroservices.beerorderservice.dto.BeerOrderPagedList;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.services.BeerOrderService;

import ch.qos.logback.core.status.Status;


@RestController
@RequestMapping("/api/v1/customers/{customerId}/")
public class BeerOrderController {
	
	private static final Integer DEFAULT_PAGE_NUMBER=0;
	private static final Integer DEFAULT_PAGE_SIZE=25;
	
	@Autowired
	private BeerOrderService beerOrderService;
	
	@GetMapping("orders")
	public BeerOrderPagedList listOrders(@PathVariable UUID customerId,
										 @RequestParam(value="pageNumber",required=false)Integer pageNumber,
										 @RequestParam(value="pageSize", required = false)Integer pageSize) {
		
		if(pageNumber== null || pageNumber<0) {
			pageNumber=DEFAULT_PAGE_NUMBER;
		}
		if(pageSize== null || pageSize<0) {
			pageSize=DEFAULT_PAGE_SIZE;
		}
		System.out.println(customerId);
		System.out.println(pageNumber);
		System.out.println(pageSize);
		System.out.println(beerOrderService);
		return beerOrderService.listOrders(customerId, PageRequest.of(pageNumber, pageSize));
	}
	
	@PostMapping("orders")
	@ResponseStatus(HttpStatus.CREATED)
	public BeerOrderDto placeOrder(@PathVariable UUID customerId,@RequestBody BeerOrderDto beerOrderDto) {
		return beerOrderService.placeOrder(customerId, beerOrderDto);
	}
	
	@GetMapping("orders/{orderId}")
    public BeerOrderDto getOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId){
        return beerOrderService.getOrderById(customerId, orderId);
    }

    @PutMapping("/orders/{orderId}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pickupOrder(@PathVariable("customerId") UUID customerId, @PathVariable("orderId") UUID orderId){
        beerOrderService.pickupOrder(customerId, orderId);
    }
	

}
