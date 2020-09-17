package com.gabrielnwogu.elevatorSystem.repositories;

import com.gabrielnwogu.elevatorSystem.domains.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
