package com.capstone.securemessaging.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstone.securemessaging.data.model.Message
import com.capstone.securemessaging.data.dao.MessageDao

/**
 * Room database for offline message storage
 * Persists messages across app restarts
 */
@Database(
    entities = [Message::class],
    version = 1,
    exportSchema = false
)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}

