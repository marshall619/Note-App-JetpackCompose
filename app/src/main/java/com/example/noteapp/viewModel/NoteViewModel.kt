package com.example.noteapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.repositorys.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository : NoteRepository
) : ViewModel() {

    val allNotes : Flow<List<Note>> = repository.allNotes

    fun createNote(note : Note){
        viewModelScope.launch {
            repository.createNote(note = note)
        }
    }

    fun deleteNote(id : Int){
        viewModelScope.launch {
            repository.deleteNote(id = id)
        }
    }

    fun updateNote(note : Note){
        viewModelScope.launch {
            repository.updateNote(note = note)
        }
    }

    fun deleteAllNotes(){
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }

}