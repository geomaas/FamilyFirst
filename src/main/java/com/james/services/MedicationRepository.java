package com.james.services;

import com.james.entities.Medication;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by user on 7/25/16.
 */
public interface MedicationRepository extends CrudRepository<Medication, Integer>{
    public Medication findByMedName(String medName);
}
