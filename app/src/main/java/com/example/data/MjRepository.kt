package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MjRepository(private val db: AppDatabase) {
    val chatMessages: Flow<List<ChatMessageEntity>> = db.chatDao().getAllMessages()
    val reminders: Flow<List<ReminderEntity>> = db.reminderDao().getAllReminders()
    val notes: Flow<List<NoteEntity>> = db.noteDao().getAllNotes()
    val settings: Flow<UserSettingsEntity?> = db.settingsDao().getSettings()

    suspend fun insertChatMessage(message: ChatMessageEntity) {
        db.chatDao().insertMessage(message)
    }

    suspend fun clearChat() {
        db.chatDao().clearChat()
    }

    suspend fun insertReminder(reminder: ReminderEntity) {
        db.reminderDao().insertReminder(reminder)
    }

    suspend fun updateReminder(reminder: ReminderEntity) {
        db.reminderDao().updateReminder(reminder)
    }

    suspend fun deleteReminder(id: Long) {
        db.reminderDao().deleteReminder(id)
    }

    suspend fun insertNote(note: NoteEntity) {
        db.noteDao().insertNote(note)
    }

    suspend fun deleteNote(id: Long) {
        db.noteDao().deleteNote(id)
    }

    suspend fun saveSettings(settings: UserSettingsEntity) {
        db.settingsDao().saveSettings(settings)
    }
}
