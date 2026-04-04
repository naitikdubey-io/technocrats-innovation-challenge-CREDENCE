package com.credence.backend.controller;

import com.credence.backend.dto.ChatRequestDTO;
import com.credence.backend.service.AiIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/v1/chat")
    @RequiredArgsConstructor
    public class ChatController {

        private final AiIntegrationService aiService;

        @PostMapping
        public String askAi(@RequestBody ChatRequestDTO request) {
            // We take the message, send it to AI, and return it immediately.
            // NO Database calls here! The data vanishes after the response is sent.
            return aiService.getLiveChatResponse(request.message());
        }
    }

