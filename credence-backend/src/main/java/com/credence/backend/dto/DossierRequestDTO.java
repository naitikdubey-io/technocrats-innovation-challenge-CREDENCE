package com.credence.backend.dto;

public record DossierRequestDTO(
        Long patientId,
        String objectiveSummary,
        String biometricData
) {

}
