package com.example.noteapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.noteapp.R
import com.example.noteapp.data.model.Note
import com.example.noteapp.ui.navigator.Screen
import com.example.noteapp.viewModel.NoteViewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ShowNoteScreens(
    viewModel: NoteViewModel = hiltViewModel(),
    navHostController: NavHostController,
    noteAsString: String,
) {
    var needToClose by remember { mutableStateOf(false) }
    var needToSave by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    val context = LocalContext.current
    val gson = Gson()
    val myNote = gson.fromJson(noteAsString, Note::class.java)



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF252525))
            .padding(horizontal = 8.dp)
    ) {
        AddNoteTopBar2(noteId = myNote.id, navHostController =  navHostController,isBackSpaceClicked = { needToClose = it }, isSaveClicked = {
            if (title == "" || body == "") {
                Toast.makeText(context, "Pleas fill title and body !", Toast.LENGTH_SHORT).show()
            } else {
                needToSave = true
            }
        })
        MakeNewNoteText(
            titleRet = { title = it },
            bodyRet = { body = it },
            bodyInitialValue = myNote.body,
            titleInitialValue = myNote.title
        )
    }

    if (needToClose) navHostController.popBackStack(route = Screen.Main.route, inclusive = false)
    if (needToSave) {
        LaunchedEffect(true) {
            val sdf = SimpleDateFormat("dd-M-yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            viewModel.deleteNote(myNote.id)
            viewModel.createNote(Note(title = title, body = body, date = "$currentDate"))
        }
        navHostController.popBackStack(route = Screen.Main.route, inclusive = false)
    }
}

@Composable
fun AddNoteTopBar2(
    noteId: Int,
    viewModel: NoteViewModel = hiltViewModel(),
    navHostController: NavHostController,
    isBackSpaceClicked: (Boolean) -> Unit,
    isSaveClicked: () -> Unit,
) {
    var needToClose by remember { mutableStateOf(false) }
    isBackSpaceClicked(needToClose)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xff3B3B3B))
                .clickable {
                    needToClose = true
                }
                .padding(horizontal = 12.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.leftarrow),
                contentDescription = "search Icon",
                modifier = Modifier
                    .size(20.dp),
                tint = Color.White
            )
        }

        Row() {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xff3B3B3B))
                    .clickable {
                        viewModel.deleteNote(noteId)
                        navHostController.popBackStack(route = Screen.Main.route, inclusive = false)
                    }
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xff3B3B3B))
                    .clickable {
                        isSaveClicked()
                    }
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Save",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}