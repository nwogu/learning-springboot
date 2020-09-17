package com.gabrielnwogu.elevatorSystem.repositories;

import com.gabrielnwogu.elevatorSystem.domains.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Order findByNumber(String number);

    List<Order> findByStatus(String status);
}
