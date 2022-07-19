package com.example.android.architecture.blueprints.todoapp.repository

class UseCaseFactory(
    val addDriverUseCase: AddContactUseCase,
    val getAllContactsUseCase: GetAllContactsUseCase,
    val removeContactUseCase: RemoveContactUseCase
)
