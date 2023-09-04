package com.example.noteapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createNote(note : Note)

    @Query("SELECT * FROM note_table")
    fun allNotes() : Flow<List<Note>>

    @Query("DELETE FROM note_table WHERE id == :id ")
    suspend fun deleteNote(id : Int)

    @Update
    suspend fun updateNote(note : Note)

    @Query("DELETE FROM NOTE_TABLE")
    suspend fun deleteAllNotes()
}