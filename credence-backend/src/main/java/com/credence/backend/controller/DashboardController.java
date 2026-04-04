package com.credence.backend.controller;

import com.credence.backend.service.ClinicalDossierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

    @Controller // Notice this is NOT a @RestController!
    @RequiredArgsConstructor
    public class DashboardController {

        private final ClinicalDossierService service;

        // When a human types "/dashboard" into Chrome, open this door
        @GetMapping("/dashboard")
        public String viewDashboard(Model model) {

            // 1. Grab all the patient records from the database
            // 2. Add them to the Thymeleaf "Model" (the box we hand to the HTML file)
            model.addAttribute("dossiers", service.getAllDossiers());

            // 3. Tell Spring Boot to find the file named "dashboard.html" and render it
            return "dashboard";
        }
    }

