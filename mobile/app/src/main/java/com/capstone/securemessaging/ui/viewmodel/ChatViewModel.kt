package com.capstone.securemessaging.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.capstone.securemessaging.data.model.Message
import com.capstone.securemessaging.data.model.DeliveryStatus
import com.capstone.securemessaging.data.model.Conversation
import com.capstone.securemessaging.data.repository.MessagingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel(
    private val repository: MessagingRepository,
    private val currentUserId: String,
    private val recipientId: String,
    private val contactName: String
) : ViewModel() {
    val messages: Flow<List<Message>> = repository.getMessagesForConversation(currentUserId, recipientId)
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun sendMessage(content: String) {
        if (content.isBlank()) return
        
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // TODO: Encrypt message content
                val encryptedContent = content // Placeholder - implement encryption
                
                val message = Message(
                    messageId = UUID.randomUUID().toString(),
                    senderId = currentUserId,
                    recipientId = recipientId,
                    encryptedContent = encryptedContent,
                    timestamp = System.currentTimeMillis(),
                    deliveryStatus = DeliveryStatus.PENDING
                )
                
                repository.insertMessage(message)
                
                // Update or create conversation
                val conversationId = "${currentUserId}_${recipientId}"
                var conversation = repository.getConversationByContactId(recipientId)
                if (conversation == null) {
                    conversation = Conversation(
                        conversationId = conversationId,
                        contactId = recipientId,
                        contactName = contactName,
                        lastMessage = content,
                        lastMessageTimestamp = message.timestamp
                    )
                } else {
                    conversation = conversation.copy(
                        lastMessage = content,
                        lastMessageTimestamp = message.timestamp
                    )
                }
                repository.insertOrUpdateConversation(conversation)
                
                // TODO: Send to backend or queue for Bluetooth forwarding
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class ChatViewModelFactory(
    private val repository: MessagingRepository,
    private val currentUserId: String,
    private val recipientId: String,
    private val contactName: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(repository, currentUserId, recipientId, contactName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

