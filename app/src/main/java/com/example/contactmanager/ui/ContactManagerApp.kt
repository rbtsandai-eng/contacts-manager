package com.example.contactmanager.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.contactmanager.ContactManagerApplication
import com.example.contactmanager.ui.navigation.Routes
import com.example.contactmanager.ui.navigation.contactFormRoute
import com.example.contactmanager.ui.screens.ContactFormScreen
import com.example.contactmanager.ui.screens.ContactsListScreen
import com.example.contactmanager.ui.theme.ContactManagerTheme

@Composable
fun ContactManagerApp() {
    ContactManagerTheme {
        val navController = rememberNavController()
        val app = LocalContext.current.applicationContext as ContactManagerApplication

        NavHost(
            navController = navController,
            startDestination = Routes.CONTACTS
        ) {
            composable(Routes.CONTACTS) {
                ContactsListScreen(
                    onAddContact = { navController.navigate(contactFormRoute()) },
                    onEditContact = { id -> navController.navigate(contactFormRoute(id)) },
                    repository = app.repository
                )
            }
            composable(
                route = Routes.CONTACT_FORM,
                arguments = listOf(
                    navArgument(Routes.CONTACT_ID_ARG) {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                val contactId = backStackEntry.arguments?.getInt(Routes.CONTACT_ID_ARG) ?: -1
                ContactFormScreen(
                    contactId = if (contactId >= 0) contactId else null,
                    repository = app.repository,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
