package com.example.listadelacompra.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ElementsEntity::class], version = 1)
abstract class ElementsDatabase: RoomDatabase() {
    abstract fun taskDao():ElementDao
}