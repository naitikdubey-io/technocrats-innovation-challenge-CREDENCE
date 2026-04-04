package com.credence.backend.service;

import com.credence.backend.dto.AiRequestDTO;
import com.credence.backend.dto.AiResponseDTO;
import com.credence.backend.dto.ChatResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AiIntegrationService {

    private final RestClient restClient;
    private final String aiServiceUrl;

    // We inject the URL from application.properties
    public AiIntegrationService(RestClient.Builder restClientBuilder,
                                @Value("${ai.service.url}") String aiServiceUrl) {
        this.restClient = restClientBuilder.build();
        this.aiServiceUrl = aiServiceUrl;
    }

    public String getAiInsights(String objectiveSummary, String biometricData) {
        System.out.println("🤖 [SYSTEM] Firing network request to Python FastAPI at: " + aiServiceUrl);

        // 1. Combine the medical data into a single sentence for the AI
        String aiPrompt = "Please analyze this patient. Summary: " + objectiveSummary +
                " | Biometrics: " + biometricData;

        // 2. Pack it into the exact variable name Python wants (user_message)
        AiRequestDTO requestPayload = new AiRequestDTO(aiPrompt);

        try {
            // 3. Fire the POST request to Python and wait for the response
            AiResponseDTO response = restClient.post()
                    .uri(aiServiceUrl)
                    .body(requestPayload)      // Spring automatically converts this DTO to JSON!
                    .retrieve()                // Execute the request
                    .body(AiResponseDTO.class); // Convert the incoming JSON back into our Java DTO

            // ... Inside the try block ...
            System.out.println("✅ [SYSTEM] Successful response received from Python AI!");
            return response != null ? response.report() : "Error: Empty response from AI.";

        } catch (Exception e) {
            // If the Python server is turned off or crashes, our Java app won't crash.
            // It just catches the error and saves a safe fallback message.
            System.err.println("❌ [SYSTEM] AI Server Offline or Unreachable: " + e.getMessage());
            return "AI DIAGNOSTIC OFFLINE: Unable to reach the Python microservice. Manual doctor review required.";
        }
    }

    public String getLiveChatResponse(String userMessage) {
        System.out.println("💬 [CHAT] Sending user message to AI: " + userMessage);

        AiRequestDTO requestPayload = new AiRequestDTO(userMessage);
        String chatUrl = aiServiceUrl.replace("/generate-report", "/chat");

        try {
            // 2. Use the new ChatResponseDTO instead of AiResponseDTO
            ChatResponseDTO response = restClient.post()
                    .uri(chatUrl)
                    .body(requestPayload)
                    .retrieve()
                    .body(ChatResponseDTO.class);

            if (response != null) {
                System.out.println("✅ [CHAT] AI Replied successfully.");
                return response.reply(); // Return the AI's words to Postman/Android
            }
            return "AI returned an empty response.";

        } catch (Exception e) {
            // 3. Print the REAL error to IntelliJ so you can debug
            System.err.println("❌ [CHAT ERROR] Connection failed: " + e.getMessage());
            return "I'm having trouble connecting. Please try again in a moment.";
        }
    }
}