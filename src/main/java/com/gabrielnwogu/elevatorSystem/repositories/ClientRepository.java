package com.gabrielnwogu.elevatorSystem.repositories;

import com.gabrielnwogu.elevatorSystem.domains.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
