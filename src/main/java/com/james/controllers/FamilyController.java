package com.james.controllers;

import com.james.entities.ProTip;
import com.james.entities.Task;
import com.james.entities.User;
import com.james.services.ProTipsRepository;
import com.james.services.TaskRepository;
import com.james.services.UserRepository;
import com.james.utils.PasswordStorage;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.sql.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jamesyburr on 7/20/16.
 */
@RestController
public class FamilyController {
    @Autowired
    UserRepository users;

    @Autowired
    TaskRepository tasks;

    @Autowired
    ProTipsRepository tips;

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

    //create page @localhost 8080/tasks
    @RequestMapping (path = "/tasks", method = RequestMethod.GET)
    public ArrayList<Task> tasks (){
//        ArrayList<Task> unfilteredtaskList = (ArrayList<Task>) tasks.findAll();
//        ArrayList<Task> taskList = new ArrayList<>();
//        LocalDateTime current = LocalDateTime.now();
//        for (Task task : unfilteredtaskList) {
//            LocalDateTime created = task.getTimestamp();
//            Duration thisDuration =  Duration.ofHours(12);
//            LocalDateTime endTime = (LocalDateTime) thisDuration.addTo(created);
//            if (endTime.isAfter(current)){
//                taskList.add(task);
//            }
//        }

        return (ArrayList<Task>) tasks.findByTimestampIsGreaterThan(LocalDateTime.now().minusHours(12));
    }

    //add a task
    @RequestMapping (path = "/addTask", method = RequestMethod.POST)
    public Task addTask(HttpSession session, @RequestBody String taskText) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findByUserName(userName);
        LocalDateTime timestamp = LocalDateTime.now();
        Task task = new Task(user, taskText, null, null, false, timestamp);
        tasks.save(task);
        System.out.println(getRandomProtip());
        return task;
    }

    @RequestMapping (path = "/comment{taskId}", method = RequestMethod.POST)
    public Task comment (@RequestBody String comment, @PathVariable int taskId) {
        Task task = tasks.findOne(taskId);
        task.setCommentText(comment);
        tasks.save(task);
        return task;
    }

    @RequestMapping (path = "/complete{taskId}", method = RequestMethod.POST)
    public Task complete (HttpSession session, @PathVariable int taskId) {
        Task task = tasks.findOne(taskId);
        User user = users.findByUserName((String) session.getAttribute("userName"));
        task.setCompletedByUser(user);
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

    @RequestMapping (path = "/logout", method = RequestMethod.POST)
    public HttpStatus logout(HttpSession session) {
        session.invalidate();

        return HttpStatus.OK;
    }

    @RequestMapping (path = "/Protip", method = RequestMethod.GET)
    public String getRandomProtip (){
//        int size = (int) tips.count();
//        Random r = new Random();
//        int pick = r.nextInt((size - 1) + 1) + 1;
//        ProTip tip = tips.findOne(pick);
        ProTip tip = tips.randomTip().iterator().next();
        String tipText = tip.getTip();
        return tipText;
    }
}
