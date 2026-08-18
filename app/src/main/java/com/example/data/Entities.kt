package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sender: String, // "user" or "mj"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val timeStr: String,
    val isCompleted: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val mood: String = "Neutral",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val avatarStyle: String = "Neon", // Classic, Neon, Cyberpunk
    val outfit: String = "Neon Suit", // Neon Suit, Casual, Elegant
    val theme: String = "Starry Night", // Starry Night, Sunset Glow, Deep Space, Cyber Matrix
    val voiceEnabled: Boolean = true
)
