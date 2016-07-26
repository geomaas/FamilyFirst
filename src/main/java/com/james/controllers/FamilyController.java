package com.james.controllers;

import com.james.entities.Medication;
import com.james.entities.ProTip;
import com.james.entities.Task;
import com.james.entities.User;
import com.james.services.MedicationRepository;
import com.james.services.ProTipsRepository;
import com.james.services.TaskRepository;
import com.james.services.UserRepository;
import com.james.utils.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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

    @Autowired
    ProTipsRepository tips;

    @Autowired
    MedicationRepository medications;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User user, HttpSession session) throws Exception {
        User userInDb = users.findFirstByUserName(user.getUserName());
        if (userInDb == null) {
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(user.getPassword(), userInDb.getPassword())) {
            throw new Exception("Incorrect user information");
        }

        session.setAttribute("userName", user.getUserName());
        return user;
    }

    //create page @localhost 8080/tasks
    //filter tasks created >12hr earlier
    //send filtered list to front-end
    @RequestMapping (path = "/tasks", method = RequestMethod.GET)
    public ArrayList<Task> tasks (){
        return (ArrayList<Task>) tasks.findByTimestampIsGreaterThan(LocalDateTime.now().minusHours(12));
    }

    //add a task
    //get user info from session
    //set current time
    //create new task object
    //save to table
    //return new task to front-end
    @RequestMapping (path = "/addTask", method = RequestMethod.POST)
    public Task addTask(HttpSession session, @RequestBody String taskText) {
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByUserName(userName);
        LocalDateTime timestamp = LocalDateTime.now();
        Task task = new Task(user, taskText, null, null, timestamp);
        tasks.save(task);
        return task;
    }

    //get task id from front-end via query param
    //set comment text in table
    //return task to front-end
    @RequestMapping (path = "/comment{taskId}", method = RequestMethod.POST)
    public Task comment (@RequestBody String comment, @PathVariable int taskId) {
        Task task = tasks.findOne(taskId);
        task.setCommentText(comment);
        tasks.save(task);
        return task;
    }

    //get task id from front-end via query param
    //get user from session
    //set completed by user id in table
    @RequestMapping (path = "/complete{taskId}", method = RequestMethod.POST)
    public Task complete (HttpSession session, @PathVariable int taskId) {
        Task task = tasks.findOne(taskId);
        User user = users.findFirstByUserName((String) session.getAttribute("userName"));
        task.setCompletedByUser(user);
        tasks.save(task);
        return task;
    }

    //currently no logout button
    //logout should invalidate the session
    @RequestMapping (path = "/logout", method = RequestMethod.POST)
    public HttpStatus logout(HttpSession session) {
        session.invalidate();
        return HttpStatus.OK;
    }

    @RequestMapping (path = "/Protip", method = RequestMethod.GET)
    public ProTip getRandomProtip (){
        ProTip tip = tips.randomTip().iterator().next();
        return tip;
    }

    //create page @localhost 8080/meds
    //send array list of all meds to front-end
    @RequestMapping (path = "/meds", method = RequestMethod.GET)
    public ArrayList<Medication> meds (){
        ArrayList<Medication> medsList= (ArrayList<Medication>) medications.findAll();
        return medsList;
    }

    //post route to add a medication to the medications table
    //triggered by add button on /meds page
    @RequestMapping (path = "/addMed", method = RequestMethod.POST)
    public Medication addMed (@RequestBody Medication med){
        medications.save(med);
        return med;
    }

    @RequestMapping (path = "/given{medId}", method = RequestMethod.POST)
    public Medication given (@PathVariable int medId){
        Medication med = medications.findOne(medId);
        med.setLastGiven(LocalDateTime.now());
        medications.save(med);
        return med;
    }
}
