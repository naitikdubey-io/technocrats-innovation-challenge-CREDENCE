package com.credence.backend.service;

import com.credence.backend.entity.TemporaryAccessSession;
import com.credence.backend.repository.AccessSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

    @Service
    @RequiredArgsConstructor
    public class AccessSessionService {

        private final AccessSessionRepository repository;

        // Creates the link
        public TemporaryAccessSession createSession(Long userId, int minutes) {
            TemporaryAccessSession session = new TemporaryAccessSession();
            session.setUserId(userId);
            session.setPin(String.format("%04d", new Random().nextInt(10000)));
            session.setExpiryTime(LocalDateTime.now().plusMinutes(minutes));
            return repository.save(session);
        }

        // Checks if the link is still valid
        public Optional<TemporaryAccessSession> validate(UUID accessId, String pin) {
            return repository.findById(accessId)
                    .filter(s -> s.getExpiryTime().isAfter(LocalDateTime.now()))
                    .filter(s -> s.getPin().equals(pin));
        }
    }

