package com.efs.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AIFeedbackService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateFeedback(String description) {
        if (description == null || description.trim().length() < 15) {
            return "{\"score\":0, \"summary\":\"Pitch too short.\", \"strengths\":[], \"weaknesses\":[], \"nextSteps\":[\"Provide more details.\"]}";
        }

        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        String prompt = "You are a top-tier venture capitalist and startup advisor. Analyze this pitch: '" + description + "'. " +
                "Respond strictly with ONLY a valid JSON object. Do not use Markdown, backticks, or outside text. " +
                "The JSON must have exact keys: \"score\" (number out of 100), \"summary\" (1 sentence), " +
                "\"strengths\" (array of 2 points), \"weaknesses\" (array of 2 points), and \"nextSteps\" (array of 2 actionable advice points).";

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt))))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(apiUrl, requestEntity, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);

            String aiText = rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

            return aiText.replace("```json", "").replace("```", "").trim();

        } catch (Exception e) {
            System.out.println("AI Connection Failed: " + e.getMessage());
            return "{\"score\":0, \"summary\":\"AI Error.\", \"strengths\":[], \"weaknesses\":[], \"nextSteps\":[]}";
        }
    }

    public String chatWithAI(String userMessage) {
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        String prompt = "You are 'ClarityBot', an expert startup mentor for an platform that connects entrepreneurs with industry experts. Answer the user's question helpfully, concisely, and professionally. \n\nUser: " + userMessage;

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt))))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            String response = restTemplate.postForObject(apiUrl, requestEntity, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);

            return rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
        } catch (Exception e) {
            return "I am currently having trouble connecting to my knowledge base. Please try again.";
        }
    }

}