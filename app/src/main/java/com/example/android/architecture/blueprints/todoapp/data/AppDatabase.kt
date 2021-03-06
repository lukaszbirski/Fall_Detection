package com.example.android.architecture.blueprints.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.architecture.blueprints.todoapp.data.dao.ContactDao
import com.example.android.architecture.blueprints.todoapp.data.model.ContactEntity

@Database(entities = [ContactEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        val DATABASE_NAME: String = "falldetector_db"
    }
}
