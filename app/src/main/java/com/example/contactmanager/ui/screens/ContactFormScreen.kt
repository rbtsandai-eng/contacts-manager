package com.example.contactmanager.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contactmanager.R
import com.example.contactmanager.data.ContactRepository
import com.example.contactmanager.viewmodel.ContactFormViewModel
import com.example.contactmanager.viewmodel.ContactFormViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactFormScreen(
    contactId: Int?,
    repository: ContactRepository,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: ContactFormViewModel = viewModel(
        key = "contactForm_${contactId ?: "new"}",
        factory = ContactFormViewModelFactory(repository, contactId)
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) {
            viewModel.consumeSaveSuccess()
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.isEditing) {
                            stringResource(R.string.edit_contact_title)
                        } else {
                            stringResource(R.string.add_contact_title)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.firstName,
                onValueChange = viewModel::updateFirstName,
                label = { Text(stringResource(R.string.first_name)) },
                isError = uiState.firstNameError != null,
                supportingText = {
                    if (uiState.firstNameError != null) {
                        Text(uiState.firstNameError!!)
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.lastName,
                onValueChange = viewModel::updateLastName,
                label = { Text(stringResource(R.string.last_name)) },
                isError = uiState.lastNameError != null,
                supportingText = {
                    if (uiState.lastNameError != null) {
                        Text(uiState.lastNameError!!)
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.phone,
                onValueChange = viewModel::updatePhone,
                label = { Text(stringResource(R.string.phone)) },
                isError = uiState.phoneError != null,
                supportingText = {
                    if (uiState.phoneError != null) {
                        Text(uiState.phoneError!!)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::updateEmail,
                label = { Text(stringResource(R.string.email)) },
                isError = uiState.emailError != null,
                supportingText = {
                    if (uiState.emailError != null) {
                        Text(uiState.emailError!!)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = viewModel::saveContact,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (uiState.isEditing) {
                        stringResource(R.string.update_contact)
                    } else {
                        stringResource(R.string.save_contact)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.required_note),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
