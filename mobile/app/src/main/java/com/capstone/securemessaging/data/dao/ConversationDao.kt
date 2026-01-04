package com.capstone.securemessaging.data.dao

import androidx.room.*
import com.capstone.securemessaging.data.model.Conversation
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for conversations
 */
@Dao
interface ConversationDao {
    @Query("SELECT * FROM conversations ORDER BY isPinned DESC, lastMessageTimestamp DESC")
    fun getAllConversations(): Flow<List<Conversation>>

    @Query("SELECT * FROM conversations WHERE conversationId = :conversationId LIMIT 1")
    suspend fun getConversationById(conversationId: String): Conversation?

    @Query("SELECT * FROM conversations WHERE conversationId = :conversationId LIMIT 1")
    fun getConversationByIdFlow(conversationId: String): Flow<Conversation?>

    @Query("SELECT * FROM conversations WHERE contactId = :contactId LIMIT 1")
    suspend fun getConversationByContactId(contactId: String): Conversation?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: Conversation)

    @Update
    suspend fun updateConversation(conversation: Conversation)

    @Delete
    suspend fun deleteConversation(conversation: Conversation)

    @Query("DELETE FROM conversations WHERE conversationId = :conversationId")
    suspend fun deleteConversationById(conversationId: String)

    @Query("UPDATE conversations SET unreadCount = 0 WHERE conversationId = :conversationId")
    suspend fun markAsRead(conversationId: String)
}

