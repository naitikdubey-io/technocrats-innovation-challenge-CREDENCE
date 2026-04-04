
package com.credence.backend.repository;

import com.credence.backend.entity.ClinicalDossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface ClinicalDossierRepository extends JpaRepository<ClinicalDossier, Long> {
        // By extending JpaRepository, Spring Boot automatically gives us
        // all the basic CRUD (Create, Read, Update, Delete) operations for free!
    }

