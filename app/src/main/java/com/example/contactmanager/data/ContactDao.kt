package com.example.contactmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query(
        "SELECT * FROM contacts ORDER BY lastName COLLATE NOCASE ASC, firstName COLLATE NOCASE ASC"
    )
    fun getAllSorted(): Flow<List<ContactEntity>>

    @Query(
        """
        SELECT * FROM contacts
        WHERE LOWER(firstName) LIKE '%' || LOWER(:query) || '%'
           OR LOWER(lastName) LIKE '%' || LOWER(:query) || '%'
           OR phone LIKE '%' || :query || '%'
        ORDER BY lastName COLLATE NOCASE ASC, firstName COLLATE NOCASE ASC
        """
    )
    fun search(query: String): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): ContactEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: ContactEntity): Long

    @Update
    suspend fun update(contact: ContactEntity)

    @Delete
    suspend fun delete(contact: ContactEntity)
}
