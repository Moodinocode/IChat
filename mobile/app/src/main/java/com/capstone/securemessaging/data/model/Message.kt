package com.capstone.securemessaging.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Message data model
 * Represents a message in the system with UUID for deduplication
 */
@Entity(tableName = "messages")
data class Message(
    @PrimaryKey
    val messageId: String = UUID.randomUUID().toString(),
    val senderId: String,
    val recipientId: String,
    val encryptedContent: String,
    val timestamp: Long = System.currentTimeMillis(),
    val deliveryStatus: DeliveryStatus = DeliveryStatus.PENDING,
    val hopCount: Int = 0,
    val visitedDevices: List<String> = emptyList(),
    val isFromBluetooth: Boolean = false
)

enum class DeliveryStatus {
    PENDING,
    DELIVERED,
    READ
}

