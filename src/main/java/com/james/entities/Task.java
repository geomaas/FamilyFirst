package com.james.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by jamesyburr on 7/20/16.
 */
@Entity
@Table(name = "tasks")
public class Task {

    int id;

    String taskText;

    String commentText;

    @ManyToOne
    User user;
}
