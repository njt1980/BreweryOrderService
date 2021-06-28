package com.nimel.mymicroservices.beerorderservice.services;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;
import javax.sound.sampled.Line;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nimel.mymicroservices.common.dtos.BeerOrderDto;
import com.nimel.mymicroservices.common.dtos.BeerOrderPagedList;
import com.nimel.mymicroservices.beerorderservice.entity.BeerOrder;
import com.nimel.mymicroservices.beerorderservice.entity.Customer;
import com.nimel.mymicroservices.beerorderservice.entity.OrderStatusEnum;
import com.nimel.mymicroservices.beerorderservice.mappers.BeerOrderMapper;
import com.nimel.mymicroservices.beerorderservice.respository.BeerOrderRepository;
import com.nimel.mymicroservices.beerorderservice.respository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerOrderServiceImpl implements BeerOrderService {
	
	private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final BeerOrderManager beerOrderManager;
    private final ApplicationEventPublisher publisher;

	@Override
	public BeerOrderPagedList listOrders(UUID customerId, Pageable pageable) {
		
		Optional<Customer> customerOptional=customerRepository.findById(customerId);
		
		if(customerOptional.isPresent()) {
			Page<BeerOrder> page = beerOrderRepository.findAllByCustomer(customerOptional.get(), pageable);
			return new BeerOrderPagedList(page
					.stream()
					.map(beerOrderMapper::toBeerOrderDtoWithDetails)
					.collect(Collectors.toList())
					,PageRequest.of(page.getPageable().getPageNumber(),page.getPageable().getPageSize())
					,page.getTotalElements());
		}
		else {
			return null;
		}
	}

	@Transactional
	@Override
	public BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto) {
		
		Optional<Customer> customerOptional= customerRepository.findById(customerId);
		
		if(customerOptional.isPresent()){
			BeerOrder beerOrder=beerOrderMapper.toBeerOrder(beerOrderDto);
			beerOrder.setCustomer(customerOptional.get());
			beerOrder.setOrderStatus(OrderStatusEnum.NEW);
			beerOrder.getBeerOrderLines().forEach(line -> line.setBeerOrder(beerOrder));
			BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);
//			BeerOrder savedBeerOrder=beerOrderRepository.saveAndFlush(beerOrder);
			return beerOrderMapper.toBeerOrderDto(savedBeerOrder);
		}else {
			throw new RuntimeException("Customer not found");
		}
		
	}

	@Override
	public BeerOrderDto getOrderById(UUID customerId, UUID orderId) {
		return beerOrderMapper.toBeerOrderDto(getOrder(customerId, orderId));
		}
	
	@Override
	public void pickupOrder(UUID customerId, UUID orderId) {
		
		beerOrderManager.beerOrderPickedUp(orderId);
//		BeerOrder beerOrder= getOrder(customerId, orderId);
//		beerOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);
	}

	private BeerOrder getOrder(UUID customerId, UUID orderId) {
		Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if(customerOptional.isPresent()){
            Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(orderId);

            if(beerOrderOptional.isPresent()){
                BeerOrder beerOrder = beerOrderOptional.get();

                // fall to exception if customer id's do not match - order not for customer
                if(beerOrder.getCustomer().getId().equals(customerId)){
                    return beerOrder;
                }
            }
            throw new RuntimeException("Beer Order Not Found");
        }
        throw new RuntimeException("Customer Not Found");
	}



}
