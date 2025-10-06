package com.taskManager.todo.todo_manager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;


public class ToDo {


    private int id;
    private String title;
    private String content;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    private String status;

    private Date createdAt;

    @JsonFormat(pattern = "dd/mm/yyyy")
    private Date targetDate;


    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", targetDate=" + targetDate +
                '}';
    }

    public ToDo(int id, String title, String status, String content, Date createdAt, Date targetDate) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.content = content;
        this.createdAt = createdAt;
        this.targetDate = targetDate;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }


}
