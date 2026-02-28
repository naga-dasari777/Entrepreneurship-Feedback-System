package com.efs.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/security")
public class IdeaSecurityController {

    @Autowired
    private IdeaSessionRepository ideaRepository;

    @Autowired
    private AIFeedbackService aiService;

    @PostMapping("/check")
    public Map<String, String> checkPlagiarism(@RequestBody Map<String, String> request) {
        String newIdea = request.get("idea");

        List<IdeaSession> allIdeas = ideaRepository.findAll();
        String existingIdeasContext = allIdeas.stream()
                .map(IdeaSession::getUserQuery)
                .collect(Collectors.joining(" || "));

        String securityPrompt = "IGNORE PREVIOUS PERSONA. You are an IP Plagiarism Scanner. " +
                "Compare this new idea: [" + newIdea + "] against our vault of existing ideas: [" + existingIdeasContext + "]. " +
                "Return a strict percentage of similarity and a 1-sentence explanation of whether it is a duplicate.";

        String aiReport = aiService.chatWithAI(securityPrompt);

        return Map.of("report", aiReport);
    }
}