package com.example.listadelacompra.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ElementDao {

    @Query("SELECT * FROM elementsList")
    fun getElements(): MutableList<ElementsEntity>

    @Insert
    suspend fun addElement(item: ElementsEntity)

    @Delete
    suspend fun deleteElement(item: ElementsEntity)

    @Update
    suspend fun updateElement(item: ElementsEntity)


}