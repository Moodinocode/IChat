package com.capstone.securemessaging.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Contact data model
 * Represents a contact/user in the messaging system
 */
@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey
    val userId: String,
    val username: String,
    val deviceId: String? = null,
    val publicKey: String? = null,
    val phoneNumber: String? = null,
    val profileImageUri: String? = null,
    val isOnline: Boolean = false,
    val lastSeen: Long? = null,
    val addedAt: Long = System.currentTimeMillis()
)

