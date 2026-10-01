package com.mohit.machinebooks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BookEntity::class],
    version = 1
)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}