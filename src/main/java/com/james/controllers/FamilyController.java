package com.james.controllers;

import com.james.entities.User;
import com.james.services.TaskRepository;
import com.james.services.UserRepository;
import com.james.utils.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by jamesyburr on 7/20/16.
 */
@RestController
public class FamilyController {
    @Autowired
    UserRepository users;

    @Autowired
    TaskRepository tasks;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public boolean login(@RequestBody User user, HttpSession session) throws Exception {
        User userInDb = users.findByUserName(user.getUserName());
        if (userInDb == null) {
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(user.getPassword(), userInDb.getPassword())) {
            throw new Exception("Incorrect user information");
        }

        session.setAttribute("userName", user.getUserName());
        return true;
    }

}
