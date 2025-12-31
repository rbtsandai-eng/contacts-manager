# Contacts Manager (Android)

A simple Android mobile application for managing contacts, developed in **Kotlin** using **MVVM architecture** and **Room Database** for persistent local storage. The UI is built with **Jetpack Compose** and supports full CRUD operations plus search.

## Features (Assignment Requirements)

- **View contacts list**: Displays all saved contacts in a list sorted alphabetically (last name, then first name).
- **Add contact**: Form with fields: First Name, Last Name, Phone, Email.
- **Edit contact**: Update an existing contact (prefilled form).
- **Delete contact**: Delete with a confirmation dialog.
- **Search contacts**: Search by name or phone.

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose (Material 3)
- **Architecture**: MVVM (Model–View–ViewModel)
- **Database**: Room (SQLite local database)
- **State**: StateFlow + Coroutines

## Project Structure (High Level)

- `data/`  
  - `ContactEntity.kt` (Room entity)  
  - `ContactDao.kt` (DAO CRUD + queries)  
  - `AppDatabase.kt` (Room database)  
  - `ContactRepository.kt` (data access abstraction)
- `viewmodel/`  
  - `ContactsViewModel.kt` (list, search, delete)  
  - `ContactFormViewModel.kt` (add/edit form, validation)
- `ui/`  
  - `screens/` (Contacts list & form screens)  
  - `navigation/` (routes)  
  - `theme/` (Compose theme)

## How to Run

1. Open the project root folder in **Android Studio**.
2. Wait for **Gradle Sync** to finish.
3. Select an emulator or a connected Android device.
4. Click **Run (▶)**.

## Screenshots (Optional)

Screenshots used for the report/demo can be stored in:
- `docs/screenshots/`

## Notes / Assumptions

- Sorting is performed by **last name** and then **first name** (case-insensitive).
- **First name, last name, and phone are required**. Email is optional (validated if provided).
- Data is stored locally using Room and persists between app restarts.
