package com.example.noteapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.data.Constants.NOTE_TABLE

@Entity(tableName = NOTE_TABLE)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String,
    val date : String,
    val body : String
)
