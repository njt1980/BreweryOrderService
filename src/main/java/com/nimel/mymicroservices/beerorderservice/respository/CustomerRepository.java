package com.nimel.mymicroservices.beerorderservice.respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimel.mymicroservices.beerorderservice.entity.Customer;



public interface CustomerRepository extends JpaRepository<Customer, UUID>{
	
	List<Customer> findAllByCustomerNameLike(String customerName);

}
