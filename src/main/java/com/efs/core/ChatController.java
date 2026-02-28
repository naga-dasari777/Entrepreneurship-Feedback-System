package com.efs.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private AIFeedbackService aiService;

    @Autowired
    private IdeaSessionRepository ideaRepository;

    @PostMapping
    public Map<String, String> sendChatMessage(@RequestBody Map<String, String> request, @AuthenticationPrincipal OAuth2User principal) {
        String userMessage = request.get("message");
        String aiResponse = aiService.chatWithAI(userMessage);

        if (principal != null && principal.getAttribute("name") != null) {
            IdeaSession session = new IdeaSession();
            session.setUsername(principal.getAttribute("name"));
            session.setUserQuery(userMessage);
            session.setAiResponse(aiResponse);
            ideaRepository.save(session);
        }

        return Map.of("reply", aiResponse);
    }

    @GetMapping("/vault")
    public List<IdeaSession> getVault(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null || principal.getAttribute("name") == null) {
            return List.of();
        }
        return ideaRepository.findByUsernameOrderByIdDesc(principal.getAttribute("name"));
    }
}