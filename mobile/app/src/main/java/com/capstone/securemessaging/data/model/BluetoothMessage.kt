package com.capstone.securemessaging.data.model

/**
 * Bluetooth message wrapper
 * Used for Bluetooth forwarding with TTL and loop prevention
 */
data class BluetoothMessage(
    val messageId: String,
    val senderId: String,
    val recipientId: String,
    val encryptedContent: String,
    val timestamp: Long,
    val ttl: Int = 5, // Time To Live - max hop count
    val visitedDevices: List<String> = emptyList(),
    val originalSenderDeviceId: String
)

