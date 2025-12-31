package com.example.contactmanager.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ContactRepository(
    private val dao: ContactDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getContacts(query: String): Flow<List<ContactEntity>> {
        val trimmed = query.trim()
        return if (trimmed.isEmpty()) {
            dao.getAllSorted()
        } else {
            dao.search(trimmed)
        }
    }

    suspend fun getById(id: Int): ContactEntity? {
        return withContext(ioDispatcher) { dao.getById(id) }
    }

    suspend fun insert(contact: ContactEntity) {
        withContext(ioDispatcher) { dao.insert(contact) }
    }

    suspend fun update(contact: ContactEntity) {
        withContext(ioDispatcher) { dao.update(contact) }
    }

    suspend fun delete(contact: ContactEntity) {
        withContext(ioDispatcher) { dao.delete(contact) }
    }
}
