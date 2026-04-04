package com.credence.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.credence.app.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = CredenceRepository()


    // Loading state for our Lottie animations
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Global Error Catcher (Prevents app crashes)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Active Chat State
    private val _chatResponse = MutableStateFlow<ChatResponse?>(null)
    val chatResponse: StateFlow<ChatResponse?> = _chatResponse.asStateFlow()

    // Final Dossier State
    private val _finalReport = MutableStateFlow<FinalReportResponse?>(null)
    val finalReport: StateFlow<FinalReportResponse?> = _finalReport.asStateFlow()

    // Timeline Ledger Data
    private val _timelineLogs = MutableStateFlow<List<LogResponse>>(emptyList())
    val timelineLogs: StateFlow<List<LogResponse>> = _timelineLogs.asStateFlow()

    // Secure QR/Share Data
    private val _shareData = MutableStateFlow<ShareResponse?>(null)
    val shareData: StateFlow<ShareResponse?> = _shareData.asStateFlow()



    fun sendChatMessage(text: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _chatResponse.value = repository.sendChatMessage(text)
            } catch (e: Exception) {
                _errorMessage.value = "Chat Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun completeChatSession() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _finalReport.value = repository.completeSession()
            } catch (e: Exception) {
                _errorMessage.value = "Synthesis Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchTimeline() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _timelineLogs.value = repository.getAllLogs()
            } catch (e: Exception) {
                _errorMessage.value = "Timeline Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun generateZeroTrustLink(logIds: List<Long>) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _shareData.value = repository.generateShareLink(logIds)
            } catch (e: Exception) {
                _errorMessage.value = "Security Handoff Failed: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun clearError() { _errorMessage.value = null }
    fun resetShareData() { _shareData.value = null }
    fun clearChatSession() {
        _chatResponse.value = null
        _finalReport.value = null
    }
}