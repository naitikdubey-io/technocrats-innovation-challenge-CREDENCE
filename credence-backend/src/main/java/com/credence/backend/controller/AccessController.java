package com.credence.backend.controller;

import com.credence.backend.entity.TemporaryAccessSession;
import com.credence.backend.service.AccessSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

    @RestController
    @RequestMapping("/api/v1/access")
    @RequiredArgsConstructor
    public class AccessController {

        private final AccessSessionService accessService;

        @PostMapping("/generate")
        public ResponseEntity<?> generate(@RequestBody Map<String, Object> payload) {
            Long userId = Long.valueOf(payload.get("userId").toString());
            int mins = Integer.parseInt(payload.get("minutes").toString());

            TemporaryAccessSession session = accessService.createSession(userId, mins);

            return ResponseEntity.ok(Map.of(
                    "pin", session.getPin(),
                    "link", "http://localhost:8080/api/v1/access/verify/" + session.getAccessId()
            ));
        }

        @GetMapping("/verify/{accessId}")
        public ResponseEntity<?> verify(@PathVariable UUID accessId, @RequestParam String pin) {
            return accessService.validate(accessId, pin)
                    .map(s -> ResponseEntity.ok("ACCESS GRANTED. Viewing data for User ID: " + s.getUserId()))
                    .orElse(ResponseEntity.status(401).body("Link Expired or Invalid PIN"));
        }
    }

