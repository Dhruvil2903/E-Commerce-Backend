package com.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.Entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

	public Optional<Order> findByOrderName(String orderName); 
}
