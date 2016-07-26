package com.james.entities;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    LocalDateTime timestamp;

    public Task() {
    }

    public Task(User user, String taskText, User completedByUser, String commentText, LocalDateTime timestamp) {
        this.taskId = taskId;
        this.user = user;
        this.taskText = taskText;
        this.completedByUser = completedByUser;
        this.commentText = commentText;
        this.timestamp = timestamp;
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

    public void setCompletedByUser(User completedByUser) {
        this.completedByUser = completedByUser;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public LocalDateTime getTimestamp() { return timestamp;}

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }
}
