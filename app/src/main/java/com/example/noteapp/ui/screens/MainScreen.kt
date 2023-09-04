package com.example.noteapp.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.noteapp.data.model.Note
import com.example.noteapp.data.model.NoteColor
import com.example.noteapp.ui.navigator.Screen
import com.example.noteapp.ui.theme.NoteColors
import com.google.gson.Gson

@Composable
fun MainScreen(allNotes: List<Note> , navHostController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    var isSearching by remember { mutableStateOf(false) }
    var searchedText by remember { mutableStateOf("") }
    
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingButton{
                navHostController.navigate(route = Screen.AddNoteScreen.route)
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFF252525))
        ) {
            if(isSearching) Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            MainTopBar(isSearching = {isSearching = it}, searchedText = {searchedText = it})
            
            if (!isSearching){ 
                NotesShow(allNotes , navHostController)
            }else{
                NotesShow(allNotes = noteSearch(allNotes , searchedText) , navHostController)
            }

        }
    }
}


@Composable
fun MainTopBar(isSearching : (Boolean) -> Unit, searchedText : (String) -> Unit) {
    var isSearchOpen by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    isSearching(isSearchOpen)
    searchedText(text)

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(if (isSearchOpen) Color(0xff3B3B3B) else Color.Transparent)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            if (!isSearchOpen){
                Text(
                    text = "Notes",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontFamily = FontFamily.Monospace,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(if (isSearchOpen) 1f else 0.17f)
                    .clip(if (isSearchOpen) RoundedCornerShape(0.dp) else RoundedCornerShape(8.dp))
                    .background(Color(0xff3B3B3B))
                    .padding(6.dp)
                    .animateContentSize(
                        tween(
                            durationMillis = 1000
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (!isSearchOpen){
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search Icon",
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                isSearchOpen = true
                            },
                        tint = Color.White
                    )
                }

                if (isSearchOpen) {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = { Text(text = "Search Note...") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            placeholderColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            textColor = Color.White,
                            cursorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .height(50.dp)
                            .weight(3f)
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                if (text == "") {
                                    isSearchOpen = false
                                } else {
                                    text = ""
                                }

                            },
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun FloatingButton(isClickedOnButton : () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(CircleShape)
            .background(Color(0xFF0F0F0F))
            .padding(14.dp)
            .clickable {
                isClickedOnButton()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "add icon",
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
    }
}


@Composable
fun NotesShow(allNotes: List<Note> , navHostController: NavHostController) {

    if (allNotes.isNotEmpty()) {
        var counter = 0
        var noteColorArray  = emptyList<NoteColor>()
        allNotes.forEach {
            noteColorArray = noteColorArray + NoteColor(it , NoteColors[counter])
            counter++
            if (counter == 6 ) counter = 0
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2)){
            items(noteColorArray){
                SimpleNoteBox(noteWithColor = it ,navHostController =  navHostController)
            }
        }
    }
}


@Composable
fun SimpleNoteBox(noteWithColor: NoteColor, modifier: Modifier = Modifier, navHostController: NavHostController) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(0.5f)
            //.width(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(noteWithColor.color)
            .clickable {
                val gson = Gson()
                val noteAsString = gson.toJson(noteWithColor.note)
                navHostController.navigate(route = Screen.ShowNoteScreen.withArgs(noteAsString))
            }
            .padding(16.dp)
    ) {
        Text(text = noteWithColor.note.title, fontSize = 22.sp, fontFamily = FontFamily.SansSerif )
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart){
            Text(
                text = noteWithColor.note.date,
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace,
                color = Color(0x88000000)
            )
        }
    }
}