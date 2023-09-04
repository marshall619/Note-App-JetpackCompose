package com.example.noteapp.ui.screens


import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.noteapp.MainActivity
import com.example.noteapp.R
import com.example.noteapp.data.model.Note
import com.example.noteapp.ui.navigator.Screen
import com.example.noteapp.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun AddNoteScreens(
    navHostController: NavHostController,
    viewModel: NoteViewModel = hiltViewModel()
) {
    var needToClose by remember{ mutableStateOf(false) }
    var needToSave by remember{ mutableStateOf(false) }
    var title by remember{ mutableStateOf("") }
    var body by remember{ mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF252525))
            .padding(horizontal = 8.dp)
    ) {
        AddNoteTopBar(isBackSpaceClicked = {needToClose = it}, isSaveClicked = {
            if (title == "" || body == ""){
                Toast.makeText(context , "Pleas fill title and body !" , Toast.LENGTH_SHORT).show()
            }else{
                needToSave = true
            }
        })
        MakeNewNoteText(titleRet = {title = it}, bodyRet = {body = it})
    }

    if(needToClose) navHostController.popBackStack(route = Screen.Main.route, inclusive = false)
    if (needToSave){
        LaunchedEffect(true){
            val sdf = SimpleDateFormat("dd-M-yyyy hh:mm:ss")
            val currentDate = sdf.format(Date()).toString()
            viewModel.createNote(Note(title = title , body =  body , date = "$currentDate"))
        }
        navHostController.popBackStack(route = Screen.Main.route, inclusive = false)
    }
}


@Composable
fun AddNoteTopBar(isBackSpaceClicked: (Boolean) -> Unit, isSaveClicked: () -> Unit) {
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

@Composable
fun MakeNewNoteText(
    titleInitialValue: String = "",
    bodyInitialValue: String = "",
    titleRet: (String) -> Unit,
    bodyRet: (String) -> Unit,
) {
    var title by remember { mutableStateOf(titleInitialValue) }
    var body by remember { mutableStateOf(bodyInitialValue) }
    titleRet(title)
    bodyRet(body)

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = title,
            onValueChange = { title = it },
            placeholder = {
                Text(
                    text = "Title",
                    fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color(0xff3B3B3B)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                placeholderColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                textColor = Color.White,
                cursorColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier
                .height(50.dp)
                .weight(1f)
        )
        Divider(color = Color(0xff3B3B3B), thickness = 2.dp)
        TextField(
            value = body,
            onValueChange = { body = it },
            placeholder = {
                Text(
                    text = "Type something...",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color(0xff3B3B3B)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                placeholderColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                textColor = Color.White,
                cursorColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier
                .height(50.dp)
                .weight(5f)
        )
    }
}