package com.james.controllers;

import com.james.entities.Task;
import com.james.entities.User;
import com.james.services.TaskRepository;
import com.james.services.UserRepository;
import com.james.utils.PasswordStorage;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    //create page @localhost 8080/tasks
    @RequestMapping (path = "/tasks", method = RequestMethod.GET)
    public ArrayList<Task> tasks (){
        ArrayList<Task> taskList = (ArrayList<Task>) tasks.findAll();
        return taskList;
    }

    //add a task
    @RequestMapping (path = "/addTask", method = RequestMethod.POST)
    public Task addTask(HttpSession session, String taskText) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findByUserName(userName);
        Task task = new Task(user, taskText, null, null, false);
        tasks.save(task);
        return task;
    }

    @RequestMapping (path = "/comment{taskId}", method = RequestMethod.POST)
    public Task comment (String comment, @PathVariable int taskId) {
        Task task = tasks.findOne(taskId);
        task.setCommentText(comment);
        tasks.save(task);
        return task;
    }

    @RequestMapping (path = "/hide{taskId}", method = RequestMethod.POST)
    public Task hide (@PathVariable int taskId) {
        Task task = tasks.findOne(taskId);
        task.setHidden(true);
        tasks.save(task);
        return task;
    }

    @RequestMapping (path = "/complete{taskId}", method = RequestMethod.POST)
    public Task complete (HttpSession session, @PathVariable int taskId) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findByUserName(userName);
        Task task = tasks.findOne(taskId);
        task.setCompletedByUser(user);
        tasks.save(task);
        return task;
    }

    @RequestMapping (path = "/logout", method = RequestMethod.POST)
    public HttpStatus logout(HttpSession session) {
        session.invalidate();

        return HttpStatus.OK;
    }


}
