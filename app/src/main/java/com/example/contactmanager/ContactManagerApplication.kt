package com.example.contactmanager

import android.app.Application
import com.example.contactmanager.data.AppDatabase
import com.example.contactmanager.data.ContactRepository

class ContactManagerApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val repository: ContactRepository by lazy { ContactRepository(database.contactDao()) }
}
