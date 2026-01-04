package com.capstone.securemessaging.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Conversation data model
 * Represents a chat conversation with a contact
 */
@Entity(tableName = "conversations")
data class Conversation(
    @PrimaryKey
    val conversationId: String,
    val contactId: String,
    val contactName: String,
    val lastMessage: String? = null,
    val lastMessageTimestamp: Long = System.currentTimeMillis(),
    val unreadCount: Int = 0,
    val isPinned: Boolean = false,
    val profileImageUri: String? = null
)

