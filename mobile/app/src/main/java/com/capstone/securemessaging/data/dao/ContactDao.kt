package com.capstone.securemessaging.data.dao

import androidx.room.*
import com.capstone.securemessaging.data.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for contacts
 */
@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts ORDER BY username ASC")
    fun getAllContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE userId = :userId LIMIT 1")
    suspend fun getContactById(userId: String): Contact?

    @Query("SELECT * FROM contacts WHERE userId = :userId LIMIT 1")
    fun getContactByIdFlow(userId: String): Flow<Contact?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: List<Contact>)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("DELETE FROM contacts WHERE userId = :userId")
    suspend fun deleteContactById(userId: String)
}

