package com.example.contactmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactmanager.data.ContactEntity
import com.example.contactmanager.data.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted


data class ContactsUiState(
    val searchQuery: String = "",
    val contacts: List<ContactEntity> = emptyList()
)

class ContactsViewModel(private val repository: ContactRepository) : ViewModel() {
    private val searchQuery = MutableStateFlow("")

    private val contactsFlow = searchQuery.flatMapLatest { query ->
        repository.getContacts(query)
    }

    val uiState: StateFlow<ContactsUiState> = combine(searchQuery, contactsFlow) { query, contacts ->
        ContactsUiState(searchQuery = query, contacts = contacts)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ContactsUiState())

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun deleteContact(contact: ContactEntity) {
        viewModelScope.launch {
            repository.delete(contact)
        }
    }
}
