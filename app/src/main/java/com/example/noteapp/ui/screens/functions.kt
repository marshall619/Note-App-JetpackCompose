package com.example.noteapp.ui.screens

import com.example.noteapp.data.model.Note

fun noteSearch(allNote: List<Note> , searchedText : String) : List<Note>{
    var newArray : List<Note> = emptyList()

    for (item in allNote){
        if (item.title.contains(searchedText , ignoreCase = true)){
            newArray = newArray + item
        }
    }

    return newArray
}