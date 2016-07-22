package com.james.controllers;

import com.james.entities.ProTip;
import com.james.services.ProTipsRepository;
import com.james.services.TaskRepository;
import com.james.services.UserRepository;
import com.james.utils.PasswordStorage;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by user on 7/21/16.
 */

@Controller
public class OtherController {
    @Autowired
    UserRepository users;

    @Autowired
    TaskRepository tasks;

    @Autowired
    ProTipsRepository tips;

    //create server connection
    @PostConstruct
    public void init() throws SQLException, FileNotFoundException, PasswordStorage.CannotPerformOperationException {
        Server.createWebServer().start();
        parseTips("temp.csv");
    }

    public void parseTips(String fileName) throws FileNotFoundException, PasswordStorage.CannotPerformOperationException {
        if (tips.count() == 0) {
            File tipsFile = new File(fileName);
            Scanner fileScanner = new Scanner(tipsFile);
            while (fileScanner.hasNext()) {

                String[] columns = fileScanner.nextLine().split("|");
                ProTip tip = new ProTip(Integer.valueOf(columns[0]), columns[1]);
                tips.save(tip);
            }
        }
    }
}
