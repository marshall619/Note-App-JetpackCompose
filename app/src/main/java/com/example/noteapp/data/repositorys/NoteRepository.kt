package com.example.noteapp.data.repositorys

import com.example.noteapp.data.db.NoteDao
import com.example.noteapp.data.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor (private val noteDao : NoteDao){

    val allNotes : Flow<List<Note>> = noteDao.allNotes()

    suspend fun createNote(note : Note){
        noteDao.createNote(note = note)
    }

    suspend fun deleteNote(id : Int){
        noteDao.deleteNote(id = id)
    }

    suspend fun updateNote(note : Note){
        noteDao.updateNote(note = note)
    }

    suspend fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }
}