package com.credence.app.data



class CredenceRepository {
    // Grab the API interface we built in Milestone 1
    private val api = NetworkClient.api

    // We use 'suspend' because these functions run in the background
    suspend fun sendChatMessage(message: String): ChatResponse {
        return api.sendChatMessage(ChatRequest(message))
    }

    suspend fun completeSession(): FinalReportResponse {
        return api.completeSession()
    }

    suspend fun getAllLogs(): List<LogResponse> {
        return api.getAllLogs()
    }

    suspend fun generateShareLink(logIds: List<Long>): ShareResponse {
        return api.generateShareLink(mapOf("logIds" to logIds))
    }
}