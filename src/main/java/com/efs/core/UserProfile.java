package com.efs.core;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    private String fullName;
    private String domain;
    private String role = "ENTREPRENEUR";
    private boolean isAvailable = true;

    @Column(columnDefinition = "TEXT")
    private String bio;

    public UserProfile() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}