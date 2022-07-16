package com.example.android.architecture.blueprints.todoapp.service.enum

enum class Algorithms(val number: String) {
    FIRST("1"),
    SECOND("2"),
    THIRD("3");

    companion object {
        fun getByValue(number: String) = values().find { it.number == number }
    }
}
