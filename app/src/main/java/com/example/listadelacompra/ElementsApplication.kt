package com.example.listadelacompra

import android.app.Application
import androidx.room.Room
import com.example.listadelacompra.data.ElementsDatabase

class ElementsApplication : Application() {

    companion object {
        lateinit var database: ElementsDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            ElementsDatabase::class.java,
            "elementsList")
            .build()
    }
}