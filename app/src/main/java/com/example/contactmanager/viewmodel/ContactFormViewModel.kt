package com.example.contactmanager.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactmanager.data.ContactEntity
import com.example.contactmanager.data.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class ContactFormUiState(
    val id: Int? = null,
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val isEditing: Boolean = false,
    val saveSuccess: Boolean = false
)

class ContactFormViewModel(
    private val repository: ContactRepository,
    contactId: Int?
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContactFormUiState())
    val uiState: StateFlow<ContactFormUiState> = _uiState.asStateFlow()

    init {
        val id = contactId ?: -1
        if (id >= 0) {
            viewModelScope.launch {
                val contact = repository.getById(id)
                if (contact != null) {
                    _uiState.update {
                        it.copy(
                            id = contact.id,
                            firstName = contact.firstName,
                            lastName = contact.lastName,
                            phone = contact.phone,
                            email = contact.email,
                            isEditing = true
                        )
                    }
                }
            }
        }
    }

    fun updateFirstName(value: String) {
        _uiState.update { it.copy(firstName = value, firstNameError = null) }
    }

    fun updateLastName(value: String) {
        _uiState.update { it.copy(lastName = value, lastNameError = null) }
    }

    fun updatePhone(value: String) {
        _uiState.update { it.copy(phone = value, phoneError = null) }
    }

    fun updateEmail(value: String) {
        _uiState.update { it.copy(email = value, emailError = null) }
    }

    fun saveContact() {
        val current = _uiState.value
        val trimmedFirst = current.firstName.trim()
        val trimmedLast = current.lastName.trim()
        val trimmedPhone = current.phone.trim()
        val trimmedEmail = current.email.trim()

        val firstNameError = if (trimmedFirst.isBlank()) "First name is required" else null
        val lastNameError = if (trimmedLast.isBlank()) "Last name is required" else null
        val phoneError = if (trimmedPhone.isBlank()) "Phone is required" else null
        val emailError = if (trimmedEmail.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            "Invalid email"
        } else {
            null
        }

        if (firstNameError != null || lastNameError != null || phoneError != null || emailError != null) {
            _uiState.update {
                it.copy(
                    firstNameError = firstNameError,
                    lastNameError = lastNameError,
                    phoneError = phoneError,
                    emailError = emailError
                )
            }
            return
        }

        viewModelScope.launch {
            val contact = ContactEntity(
                id = current.id ?: 0,
                firstName = trimmedFirst,
                lastName = trimmedLast,
                phone = trimmedPhone,
                email = trimmedEmail
            )
            if (current.isEditing) {
                repository.update(contact)
            } else {
                repository.insert(contact)
            }
            _uiState.update { it.copy(saveSuccess = true) }
        }
    }

    fun consumeSaveSuccess() {
        _uiState.update { it.copy(saveSuccess = false) }
    }
}
