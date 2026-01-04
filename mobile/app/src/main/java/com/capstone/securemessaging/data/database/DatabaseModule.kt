package com.capstone.securemessaging.data.database

import android.content.Context
import androidx.room.Room

/**
 * Database module for creating and providing database instance
 */
object DatabaseModule {
    private var database: MessageDatabase? = null

    fun getDatabase(context: Context): MessageDatabase {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                MessageDatabase::class.java,
                "messaging_database"
            )
                .fallbackToDestructiveMigration() // For development - remove in production
                .build()
        }
        return database!!
    }
}

