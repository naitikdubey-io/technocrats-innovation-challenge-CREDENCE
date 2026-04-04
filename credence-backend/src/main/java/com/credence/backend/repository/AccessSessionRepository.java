
    package com.credence.backend.repository;

import com.credence.backend.entity.TemporaryAccessSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

    @Repository
    public interface AccessSessionRepository extends JpaRepository<TemporaryAccessSession, UUID> {
        // Standard JpaRepository gives us save() and findById() automatically
    }

