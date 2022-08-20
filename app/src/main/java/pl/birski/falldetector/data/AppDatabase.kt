package pl.birski.falldetector.data

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.birski.falldetector.data.dao.ContactDao
import pl.birski.falldetector.data.model.ContactEntity

@Database(entities = [ContactEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        val DATABASE_NAME: String = "falldetector_db"
    }
}
