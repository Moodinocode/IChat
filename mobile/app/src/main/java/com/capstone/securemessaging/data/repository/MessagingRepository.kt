package com.capstone.securemessaging.data.repository

import com.capstone.securemessaging.data.dao.ContactDao
import com.capstone.securemessaging.data.dao.ConversationDao
import com.capstone.securemessaging.data.dao.MessageDao
import com.capstone.securemessaging.data.model.Contact
import com.capstone.securemessaging.data.model.Conversation
import com.capstone.securemessaging.data.model.Message
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing messaging data
 * Provides a clean API for data access
 */
class MessagingRepository(
    private val contactDao: ContactDao,
    private val conversationDao: ConversationDao,
    private val messageDao: MessageDao
) {
    // Contacts
    fun getAllContacts(): Flow<List<Contact>> = contactDao.getAllContacts()
    suspend fun getContactById(userId: String): Contact? = contactDao.getContactById(userId)
    suspend fun insertContact(contact: Contact) = contactDao.insertContact(contact)
    suspend fun insertContacts(contacts: List<Contact>) = contactDao.insertContacts(contacts)
    suspend fun deleteContact(contact: Contact) = contactDao.deleteContact(contact)

    // Conversations
    fun getAllConversations(): Flow<List<Conversation>> = conversationDao.getAllConversations()
    suspend fun getConversationById(conversationId: String): Conversation? = 
        conversationDao.getConversationById(conversationId)
    suspend fun getConversationByContactId(contactId: String): Conversation? = 
        conversationDao.getConversationByContactId(contactId)
    suspend fun insertOrUpdateConversation(conversation: Conversation) = 
        conversationDao.insertConversation(conversation)
    suspend fun updateConversation(conversation: Conversation) = 
        conversationDao.updateConversation(conversation)
    suspend fun markConversationAsRead(conversationId: String) = 
        conversationDao.markAsRead(conversationId)

    // Messages
    fun getMessagesForRecipient(recipientId: String): Flow<List<Message>> = 
        messageDao.getMessagesForRecipient(recipientId)
    
    fun getMessagesForConversation(userId: String, contactId: String): Flow<List<Message>> = 
        messageDao.getMessagesForConversation(userId, contactId)
    suspend fun insertMessage(message: Message) = messageDao.insertMessage(message)
    suspend fun updateMessage(message: Message) = messageDao.updateMessage(message)
    suspend fun deleteMessage(message: Message) = messageDao.deleteMessage(message)
}

