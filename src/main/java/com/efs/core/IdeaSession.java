package com.efs.core;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "idea_vault")
public class IdeaSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;

    @Column(columnDefinition = "TEXT")
    private String userQuery;

    @Column(columnDefinition = "TEXT")
    private String aiResponse;

    private LocalDateTime createdAt = LocalDateTime.now();

    public IdeaSession() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getUserQuery() { return userQuery; }
    public void setUserQuery(String userQuery) { this.userQuery = userQuery; }
    public String getAiResponse() { return aiResponse; }
    public void setAiResponse(String aiResponse) { this.aiResponse = aiResponse; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}