package com.example.project01_danp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.project01_danp.R

val fontPacifico = FontFamily(
    Font(R.font.pacifico_regular)
)

val fontRoboto = FontFamily(
    Font(R.font.roboto_regular)
)

val fontEdu = FontFamily(
    Font(R.font.edu_regular)
)

val fontNunito = FontFamily(
    Font(R.font.nunito_regular)
)

val fontDancing = FontFamily(
    Font(R.font.dancing_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)