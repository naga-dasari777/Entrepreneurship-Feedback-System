package com.efs.core;

import jakarta.persistence.*;

@Entity
@Table(name = "startup_pitches")
public class Pitch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "entrepreneur_name", length = 255)
    private String entrepreneurName;

    @Column(name = "project_title", length = 255)
    private String projectTitle;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(name = "feedback_status", columnDefinition = "TEXT")
    private String feedbackStatus;

    public Pitch() {
        this.feedbackStatus = "Pending";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntrepreneurName() {
        return entrepreneurName;
    }

    public void setEntrepreneurName(String entrepreneurName) {
        this.entrepreneurName = entrepreneurName;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }
}