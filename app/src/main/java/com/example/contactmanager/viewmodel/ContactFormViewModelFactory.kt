package com.example.contactmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactmanager.data.ContactRepository

class ContactFormViewModelFactory(
    private val repository: ContactRepository,
    private val contactId: Int?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactFormViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactFormViewModel(repository, contactId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
