package com.example.contactmanager.ui.navigation

object Routes {
    const val CONTACTS = "contacts"
    const val CONTACT_FORM_BASE = "contact_form"
    const val CONTACT_ID_ARG = "contactId"
    const val CONTACT_FORM = "$CONTACT_FORM_BASE?$CONTACT_ID_ARG={$CONTACT_ID_ARG}"
}

fun contactFormRoute(contactId: Int? = null): String {
    return if (contactId == null) {
        Routes.CONTACT_FORM_BASE
    } else {
        "${Routes.CONTACT_FORM_BASE}?${Routes.CONTACT_ID_ARG}=$contactId"
    }
}
