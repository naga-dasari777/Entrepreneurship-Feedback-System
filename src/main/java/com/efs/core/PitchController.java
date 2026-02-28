package com.efs.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pitches")
public class PitchController {

    @Autowired
    private PitchRepository pitchRepository;

    @Autowired
    private AIFeedbackService aiFeedbackService;

    @GetMapping
    public List<Pitch> getAllPitches() {
        return pitchRepository.findAll();
    }

    @PostMapping
    public Pitch submitPitch(@RequestBody Pitch pitch) {
        String smartFeedback = aiFeedbackService.generateFeedback(pitch.getDescription());
        pitch.setFeedbackStatus(smartFeedback);
        return pitchRepository.save(pitch);
    }
}