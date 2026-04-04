
package com.credence.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "clinical_dossiers")
    @Data
    @NoArgsConstructor
    public class ClinicalDossier {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // We will eventually link this to a specific Patient/User
        private Long patientId;

        // The AI-translated, objective clinical terminology
        @Column(columnDefinition = "TEXT")
        private String objectiveSummary;

        // Storing the hardware/biometric data (HRV, etc.) as a JSON string
        @Column(columnDefinition = "TEXT")
        private String biometricData;

        @Column(columnDefinition = "TEXT")
        private String aiAnalysis;

        // Automatically records the exact moment the dossier was finalized
        private LocalDateTime createdAt = LocalDateTime.now();


        public String getAiAnalysis() {
            return aiAnalysis;
        }

        public void setAiAnalysis(String aiAnalysis) {
            this.aiAnalysis = aiAnalysis;
        }
    }

