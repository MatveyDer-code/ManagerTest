package org.example;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "test")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String status;
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    public Task() {
    }

    public Task(String title, String description, LocalDateTime dateTime) {
        this.title = title;
        this.description = description;
        this.status = "TODO";
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDateTime() {
        this.dateTime = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}