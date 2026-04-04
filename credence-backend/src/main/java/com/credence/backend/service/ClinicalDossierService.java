
package com.credence.backend.service;

import com.credence.backend.dto.DossierRequestDTO;
import com.credence.backend.entity.ClinicalDossier;
import com.credence.backend.repository.ClinicalDossierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicalDossierService {

    private final ClinicalDossierRepository repository;

    // INJECT THE AI SERVICE HERE
    private final AiIntegrationService aiIntegrationService;

    public ClinicalDossier saveDossier(DossierRequestDTO dto) {
        ClinicalDossier dossier = new ClinicalDossier();
        dossier.setPatientId(dto.patientId());
        dossier.setObjectiveSummary(dto.objectiveSummary());
        dossier.setBiometricData(dto.biometricData());

        // --- NEW AI INTEGRATION STEP ---
        // Pause the save process. Ask Python for the analysis.
        String pythonAnalysis = aiIntegrationService.getAiInsights(dto.objectiveSummary(), dto.biometricData());

        // Attach the Python response to the dossier
        dossier.setAiAnalysis(pythonAnalysis);
        // -------------------------------

        // Save the complete package to PostgreSQL
        return repository.save(dossier);
    }

    public List<ClinicalDossier> getAllDossiers() {
        return repository.findAll();
    }

    public ClinicalDossier getDossierById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No dossier found with ID: " + id));
    }
}