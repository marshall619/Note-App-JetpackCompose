package com.example.noteapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.data.Constants.LOCAL_DB
import com.example.noteapp.data.model.Note

@Database(entities = [Note :: class] , version = 1 , exportSchema = false)
abstract class LocalDataBase : RoomDatabase(){
    abstract fun noteDao() : NoteDao
}


