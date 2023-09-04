package com.example.noteapp.ui.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.noteapp.data.model.Note
import com.example.noteapp.ui.screens.AddNoteScreens
import com.example.noteapp.ui.screens.MainScreen
import com.example.noteapp.ui.screens.ShowNoteScreens


@Composable
fun setupNavHost(navHostController: NavHostController , allNotes: List<Note>) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route){
            MainScreen(allNotes = allNotes, navHostController = navHostController)
        }
        composable(route = Screen.AddNoteScreen.route){
            AddNoteScreens(navHostController = navHostController)
        }
        composable(
            route = Screen.ShowNoteScreen.route + "/{note}",
            arguments = listOf(
                navArgument("note"){
                    type = NavType.StringType
                }
            )
        ){
            val note = it.arguments?.getString("note")
            ShowNoteScreens(navHostController = navHostController , noteAsString = note.toString())
        }
    }
}