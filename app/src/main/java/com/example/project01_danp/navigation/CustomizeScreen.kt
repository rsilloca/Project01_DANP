package com.example.project01_danp.navigation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project01_danp.R
import com.example.project01_danp.ui.theme.CustomViolet

@Composable
fun CustomizeScreen(
    navController: NavHostController,
    font: String,
    theme: String,
    language: String,
    change: (
        valueFontFamily: String,
        valueTheme: String,
        valueLanguage: String
    ) -> Unit
) {
    val mContext = LocalContext.current
    var fontFamilyVal = font
    var themeVal = theme
    var languageVal = language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 48.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mContext.getString(R.string.txt_alcancia_lista),
            fontWeight = FontWeight.Bold,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier.width(260.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.alcancia_app),
            contentDescription = null,
            Modifier
                .width(100.dp)
                .padding(bottom = 24.dp, top = 24.dp)
        )

        fontFamilyDropdown(fontFamilyVal) {
            Log.e(fontFamilyVal, it)
            fontFamilyVal = it
        }
        themeDropdown(themeVal) {
            Log.e(themeVal, it)
            themeVal = it
        }
        languageDropdown(languageVal) {
            Log.e(languageVal, it)
            languageVal = it
        }

        Button(
            onClick = {
                change(fontFamilyVal, themeVal, languageVal)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = CustomViolet)
        ) {
            Text(text = mContext.getString(R.string.txt_btn_crear_alcancia))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun fontFamilyDropdown(current: String, changeValue: (fontFamily: String) -> Unit) {
    val mContext = LocalContext.current

    val fontFamilyOptions = listOf("Nunito", "Roboto", "Edu")
    var fontFamilyExpanded by remember { mutableStateOf(false) }
    var fontFamilySelected by remember { mutableStateOf(current) }

    ExposedDropdownMenuBox(
        expanded = fontFamilyExpanded,
        onExpandedChange = {
            fontFamilyExpanded = !fontFamilyExpanded
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = fontFamilySelected,
            onValueChange = { },
            label = { Text(mContext.getString(R.string.txt_input_font_family)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = fontFamilyExpanded
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_text_format),
                    contentDescription = null,
                    modifier = Modifier.width(24.dp),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        ExposedDropdownMenu(
            expanded = fontFamilyExpanded,
            onDismissRequest = {
                fontFamilyExpanded = false
            }
        ) {
            fontFamilyOptions.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        fontFamilySelected = selectionOption
                        fontFamilyExpanded = false
                        changeValue(selectionOption)
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun themeDropdown(current: String, changeValue: (theme: String) -> Unit) {
    val mContext = LocalContext.current

    val themeOptions = listOf("Light", "Dark")
    var themeExpanded by remember { mutableStateOf(false) }
    var themeSelected by remember { mutableStateOf(current) }

    ExposedDropdownMenuBox(
        expanded = themeExpanded,
        onExpandedChange = {
            themeExpanded = !themeExpanded
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = themeSelected,
            onValueChange = { },
            label = { Text(mContext.getString(R.string.txt_input_tema)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = themeExpanded
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dark_mode),
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        ExposedDropdownMenu(
            expanded = themeExpanded,
            onDismissRequest = {
                themeExpanded = false
            }
        ) {
            themeOptions.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        themeSelected = selectionOption
                        themeExpanded = false
                        changeValue(selectionOption)
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun languageDropdown(current: String, changeValue: (language: String) -> Unit) {
    val mContext = LocalContext.current

    val languageOptions = listOf("Español", "English")
    var languageExpanded by remember { mutableStateOf(false) }
    var languageSelected by remember { mutableStateOf(current) }

    ExposedDropdownMenuBox(
        expanded = languageExpanded,
        onExpandedChange = {
            languageExpanded = !languageExpanded
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = languageSelected,
            onValueChange = { },
            label = { Text(mContext.getString(R.string.txt_input_idioma)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = languageExpanded
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_language),
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        ExposedDropdownMenu(
            expanded = languageExpanded,
            onDismissRequest = {
                languageExpanded = false
            }
        ) {
            languageOptions.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        languageSelected = selectionOption
                        languageExpanded = false
                        changeValue(selectionOption)
                        Log.e("changing lang", "update")
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}