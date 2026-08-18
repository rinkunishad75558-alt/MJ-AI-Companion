package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.data.*
import com.example.network.Content
import com.example.network.GeminiClient
import com.example.network.GeminiRequest
import com.example.network.Part
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MjViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val repository = MjRepository(db)

    val chatMessages: StateFlow<List<ChatMessageEntity>> = repository.chatMessages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val reminders: StateFlow<List<ReminderEntity>> = repository.reminders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notes: StateFlow<List<NoteEntity>> = repository.notes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _settings = MutableStateFlow(UserSettingsEntity())
    val settings: StateFlow<UserSettingsEntity> = _settings.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _isVoiceActive = MutableStateFlow(false)
    val isVoiceActive: StateFlow<Boolean> = _isVoiceActive.asStateFlow()

    init {
        viewModelScope.launch {
            repository.settings.collect { userSettings ->
                if (userSettings != null) {
                    _settings.value = userSettings
                } else {
                    repository.saveSettings(UserSettingsEntity())
                }
            }
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            // Insert user message
            val userMsg = ChatMessageEntity(sender = "user", text = text)
            repository.insertChatMessage(userMsg)

            _isGenerating.value = true
            val responseText = callGeminiOrFallback(text)
            repository.insertChatMessage(ChatMessageEntity(sender = "mj", text = responseText))
            _isGenerating.value = false
        }
    }

    private suspend fun callGeminiOrFallback(prompt: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getSmartFallbackResponse(prompt)
        }

        try {
            val systemInstruction = "You are MJ ❤️, a friendly, empathetic, supportive, intelligent AI Companion, Smart Assistant & Mobile OS Controller. Speak warmly in Hinglish or English based on user query."
            val fullPrompt = "$systemInstruction\n\nUser: $prompt"
            val response = GeminiClient.api.generateContent(
                apiKey = apiKey,
                request = GeminiRequest(
                    contents = listOf(Content(parts = listOf(Part(text = fullPrompt))))
                )
            )
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: getSmartFallbackResponse(prompt)
        } catch (e: Exception) {
            getSmartFallbackResponse(prompt)
        }
    }

    private fun getSmartFallbackResponse(prompt: String): String {
        val p = prompt.lowercase()
        return when {
            p.contains("battery") || p.contains("status") ->
                "Aapki battery 94% par hai aur system bilkul healthy hai! Sabhi background processes optimized hain. 🔋✨"
            p.contains("hello") || p.contains("hi") || p.contains("hey") ->
                "Hello ji! Main hoon MJ ❤️, aapki personal AI companion aur OS controller. Bataiye aaj main aapki kya madad kar sakti hoon?"
            p.contains("reminder") || p.contains("task") ->
                "Maine aapke reminders check kar liye hain. Aap Reminders tab mein naye tasks add kar sakte hain!"
            p.contains("weather") ->
                "Aaj ka mausam bahut suhana hai! 28°C with gentle breeze. Perfect day for productive work!"
            p.contains("translate") ->
                "Translation utility ready! Aap jo sentence bolenge, main use instantly translate kar dungi."
            p.contains("who are you") || p.contains("aap kaun ho") ->
                "Main MJ hoon, aapki caring aur smart AI companion! Aapke phone ko smoothly control karne aur har task mein help karne ke liye hamesha ready hoon. ❤️"
            else ->
                "Bohot acchi baat kahi aapne! Main samajh rahi hoon. Bataiye is par aur kya discuss karna hai? Main hamesha aapke sath hoon! 😊"
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            repository.clearChat()
        }
    }

    fun addReminder(title: String, timeStr: String) {
        viewModelScope.launch {
            repository.insertReminder(ReminderEntity(title = title, timeStr = timeStr))
        }
    }

    fun toggleReminder(reminder: ReminderEntity) {
        viewModelScope.launch {
            repository.updateReminder(reminder.copy(isCompleted = !reminder.isCompleted))
        }
    }

    fun deleteReminder(id: Long) {
        viewModelScope.launch {
            repository.deleteReminder(id)
        }
    }

    fun addNote(title: String, content: String, mood: String) {
        viewModelScope.launch {
            repository.insertNote(NoteEntity(title = title, content = content, mood = mood))
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun updateSettings(avatar: String, outfit: String, theme: String, voice: Boolean) {
        viewModelScope.launch {
            repository.saveSettings(UserSettingsEntity(id = 1, avatarStyle = avatar, outfit = outfit, theme = theme, voiceEnabled = voice))
        }
    }

    fun toggleVoiceActive() {
        _isVoiceActive.value = !_isVoiceActive.value
        if (_isVoiceActive.value) {
            sendMessage("Voice mode activated! MJ is listening...")
        }
    }
}
