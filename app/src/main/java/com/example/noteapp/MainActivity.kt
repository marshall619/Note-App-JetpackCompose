package com.example.noteapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.data.model.Note
import com.example.noteapp.ui.navigator.Screen
import com.example.noteapp.ui.navigator.setupNavHost
import com.example.noteapp.ui.screens.AddNoteTopBar
import com.example.noteapp.ui.screens.MainScreen
import com.example.noteapp.ui.screens.MakeNewNoteText
import com.example.noteapp.ui.theme.NoteAppTheme
import com.example.noteapp.viewModel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ObserverNoteViewModel()
                }
            }
        }
    }

    @Composable
    private fun ObserverNoteViewModel(viewModel : NoteViewModel = hiltViewModel()) {
        navController = rememberNavController()
        val allNotes by viewModel.allNotes.collectAsState(initial = emptyList())
        setupNavHost(navHostController = navController, allNotes = allNotes)
    }
}
