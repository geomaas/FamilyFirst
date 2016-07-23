package com.james.services;

import com.james.entities.ProTip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by user on 7/21/16.
 */
public interface ProTipsRepository extends CrudRepository<ProTip, Integer>{
    @Query("SELECT t FROM ProTip t ORDER BY RAND()")
    public Iterable<ProTip> randomTip();
}
