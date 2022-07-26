package pl.example.android.architecture.blueprints.todoapp.model

data class Contact(
    val id: Int?,
    val name: String,
    val surname: String,
    val prefix: String,
    val number: String
) {
    constructor() : this(null, "", "", "", "")
}
