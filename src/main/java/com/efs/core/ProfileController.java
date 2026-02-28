package com.efs.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserProfileRepository profileRepository;

    @GetMapping
    public UserProfile getProfile(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null || principal.getAttribute("name") == null) return new UserProfile();
        String username = principal.getAttribute("name");
        return profileRepository.findByUsername(username).orElseGet(() -> {
            UserProfile p = new UserProfile();
            p.setUsername(username);
            p.setFullName(username);
            return profileRepository.save(p);
        });
    }

    @PostMapping
    public UserProfile updateProfile(@RequestBody UserProfile updated, @AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("name");
        UserProfile p = profileRepository.findByUsername(username).orElse(new UserProfile());

        p.setUsername(username);
        p.setFullName(updated.getFullName());
        p.setDomain(updated.getDomain());
        p.setRole(updated.getRole());
        p.setBio(updated.getBio());

        return profileRepository.save(p);
    }
}