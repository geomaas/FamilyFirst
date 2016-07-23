package com.james.services;

import com.james.entities.Task;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

/**
 * Created by jamesyburr on 7/20/16.
 */
public interface TaskRepository extends CrudRepository<Task, Integer> {
    public Iterable<Task> findByTimestampIsGreaterThan(LocalDateTime minDate);
}
