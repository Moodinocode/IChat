package com.capstone.securemessaging.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.capstone.securemessaging.data.model.Message
import com.capstone.securemessaging.data.model.Contact
import com.capstone.securemessaging.data.model.Conversation
import com.capstone.securemessaging.data.dao.MessageDao
import com.capstone.securemessaging.data.dao.ContactDao
import com.capstone.securemessaging.data.dao.ConversationDao

/**
 * Room database for offline message storage
 * Persists messages across app restarts
 */
@Database(
    entities = [Message::class, Contact::class, Conversation::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun contactDao(): ContactDao
    abstract fun conversationDao(): ConversationDao
}

