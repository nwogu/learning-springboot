package com.gabrielnwogu.elevatorSystem.repositories;

import com.gabrielnwogu.elevatorSystem.domains.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
}
