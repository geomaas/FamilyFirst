package com.james.services;

import com.james.entities.User;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by jamesyburr on 7/20/16.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByUserName(String userName);
    public User findByIsClient(boolean client);
}
