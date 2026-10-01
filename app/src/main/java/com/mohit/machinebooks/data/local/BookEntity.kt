package com.mohit.machinebooks.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(

    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val title : String,
    val author : String,
    val category : String,
    val price : Double,


)
