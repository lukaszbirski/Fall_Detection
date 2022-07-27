package pl.birski.falldetector.data.source

import pl.birski.falldetector.data.model.ContactEntity

class AppDatabaseFake {
    val contacts = mutableListOf(
        ContactEntity(
            id = 0,
            name = "John",
            surname = "Smith",
            prefix = "+11",
            number = "123456789"
        ),
        ContactEntity(
            id = 1,
            name = "Britney",
            surname = "Spears",
            prefix = "+12",
            number = "987654321"
        )
    )
}
