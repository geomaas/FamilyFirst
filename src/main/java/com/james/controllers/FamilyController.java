package com.james.controllers;

import com.james.entities.Task;
import com.james.entities.User;
import com.james.services.TaskRepository;
import com.james.services.UserRepository;
import com.james.utils.PasswordStorage;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by jamesyburr on 7/20/16.
 */
@RestController
public class FamilyController {
    @Autowired
    UserRepository users;

    @Autowired
    TaskRepository tasks;

    //create server connection
    @PostConstruct
    public void init() throws SQLException, FileNotFoundException, PasswordStorage.CannotPerformOperationException {
        Server.createWebServer().start();
    }

    //create page @localhost 8080
    @RequestMapping (path = "/", method = RequestMethod.GET)
    public String placeholderFrontPage (){
        return  "";
    }

    @RequestMapping (path = "/tasks", method = RequestMethod.GET)
    public ArrayList<Task> tasks (){
        ArrayList<Task> taskList = (ArrayList<Task>) tasks.findAll();
        return taskList;
    }
}
