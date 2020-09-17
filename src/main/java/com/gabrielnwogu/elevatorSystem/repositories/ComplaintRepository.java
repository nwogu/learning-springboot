package com.gabrielnwogu.elevatorSystem.repositories;

import com.gabrielnwogu.elevatorSystem.domains.Complaint;
import org.springframework.data.repository.CrudRepository;

public interface ComplaintRepository extends CrudRepository<Complaint, Long> {
}
