package com.example.noteapp.ui.navigator

sealed class Screen(val route : String){
    object Main : Screen(route = "Home_screen")
    object AddNoteScreen : Screen(route = "addNote_screen")
    object ShowNoteScreen : Screen(route = "Show_screen")
    fun withArgs(vararg arg : String) : String{
        return buildString {
            append(route)
            arg.forEach {arg ->
                append("/$arg")
            }
        }
    }

}
