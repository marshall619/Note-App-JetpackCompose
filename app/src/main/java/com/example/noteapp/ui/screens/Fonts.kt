package com.example.noteapp.ui.screens

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.noteapp.R

val gothicA1 = FontFamily(
    listOf(
        Font(R.font.gothica1_regular, FontWeight.Normal),
        Font(R.font.gothica1_medium, FontWeight.Medium),
        Font(R.font.gothica1_semibold, FontWeight.SemiBold),
        Font(R.font.gothica1_bold, FontWeight.Bold),
        Font(R.font.gothica1_black, FontWeight.Black),
    )
)
val dyna = FontFamily(
    Font(R.font.dynabould, FontWeight.Bold),
    Font(R.font.dynamediom, FontWeight.Medium),
    Font(R.font.dynareg, FontWeight.Normal),
    Font(R.font.dynasimibold, FontWeight.SemiBold)
)
val incon = FontFamily(
    Font(R.font.inconsolatasimple, FontWeight.Normal)
)
val persionFont = FontFamily(
    Font(R.font.vazirmedium, FontWeight.Normal)
)