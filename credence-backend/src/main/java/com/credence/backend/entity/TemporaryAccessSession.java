
package com.credence.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

    @Entity
    @Data
    public class TemporaryAccessSession {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID accessId; // The unique ID in the URL

        private Long userId; // The ID of the patient
        private String pin; // The 4-digit PIN
        private LocalDateTime expiryTime; // When the link dies
    }

