package com.example.listadelacompra.data.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "elementsList",
    indices = [Index(value = ["id"], unique = true)]
)
data class ElementsEntity(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") var id: Long = 0,
    @SerializedName("element")var element: String,
    @SerializedName("complete")var complete: Boolean
)