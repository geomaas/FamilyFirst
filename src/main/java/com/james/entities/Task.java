package com.james.entities;

import javax.persistence.*;

/**
 * Created by jamesyburr on 7/20/16.
 */
@Entity
@Table(name = "tasks")
public class Task {
    @GeneratedValue
    @Id
    int taskId;

    @ManyToOne
    User user;

    @Column(nullable = false)
    String taskText;

    @ManyToOne
    User completedByUser;

    String commentText;

    @Column(nullable = false)
    boolean hidden;

    public Task() {
    }

    public Task(User user, String taskText, User completedByUser, String commentText, boolean hidden) {
        this.taskId = taskId;
        this.user = user;
        this.taskText = taskText;
        this.completedByUser = completedByUser;
        this.commentText = commentText;
        this.hidden = hidden;
    }

    public int getTaskId() {
        return taskId;
    }

    public User getUser() {
        return user;
    }

    public String getTaskText() {
        return taskText;
    }

    public User getCompletedByUser() {
        return completedByUser;
    }

    public String getCommentText() {
        return commentText;
    }

    public boolean isHidden() {
        return hidden;
    }
}
