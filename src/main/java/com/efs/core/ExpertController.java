package com.efs.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/experts")
public class ExpertController {

    @Autowired
    private UserProfileRepository profileRepository;

    @GetMapping
    public List<UserProfile> getRealExperts() {
        return profileRepository.findByRole("EXPERT");
    }
}