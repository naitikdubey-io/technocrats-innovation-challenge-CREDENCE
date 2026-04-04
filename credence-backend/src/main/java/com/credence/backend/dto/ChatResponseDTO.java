   package com.credence.backend.dto;

    // This matches Python's @app.post("/chat") return {"reply": ...}
    public record ChatResponseDTO(String reply) {
    }

