package com.capstone.securemessaging.data.dao

import androidx.room.*
import com.capstone.securemessaging.data.model.Message
import com.capstone.securemessaging.data.model.DeliveryStatus
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for messages
 * Handles CRUD operations for offline message storage
 */
@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE recipientId = :recipientId ORDER BY timestamp ASC")
    fun getMessagesForRecipient(recipientId: String): Flow<List<Message>>

    @Query("SELECT * FROM messages WHERE (senderId = :userId AND recipientId = :contactId) OR (senderId = :contactId AND recipientId = :userId) ORDER BY timestamp ASC")
    fun getMessagesForConversation(userId: String, contactId: String): Flow<List<Message>>

    @Query("SELECT * FROM messages WHERE deliveryStatus = :status")
    fun getMessagesByStatus(status: DeliveryStatus): Flow<List<Message>>

    @Query("SELECT * FROM messages WHERE messageId = :messageId LIMIT 1")
    suspend fun getMessageById(messageId: String): Message?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: Message)

    @Update
    suspend fun updateMessage(message: Message)

    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("DELETE FROM messages WHERE messageId = :messageId")
    suspend fun deleteMessageById(messageId: String)
}

