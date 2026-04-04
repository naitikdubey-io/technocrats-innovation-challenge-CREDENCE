
package com.credence.backend.controller;

import com.credence.backend.dto.DossierRequestDTO;
import com.credence.backend.entity.ClinicalDossier;
import com.credence.backend.service.ClinicalDossierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dossiers")
@RequiredArgsConstructor
public class ClinicalDossierController {

    private final ClinicalDossierService service;

    @PostMapping
    public ResponseEntity<ClinicalDossier> createDossier(@RequestBody DossierRequestDTO dto) {
        ClinicalDossier savedDossier = service.saveDossier(dto);
        return ResponseEntity.ok(savedDossier);
    }
    @GetMapping
    public ResponseEntity<List<ClinicalDossier>> getAllDossiers() {
        return ResponseEntity.ok(service.getAllDossiers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClinicalDossier> getDossierById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDossierById(id));
    }
}