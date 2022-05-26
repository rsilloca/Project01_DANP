package com.example.project01_danp.navigation

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project01_danp.MainActivity
import com.example.project01_danp.R
import com.example.project01_danp.ui.theme.CustomGray
import com.example.project01_danp.ui.theme.CustomGreen
import com.example.project01_danp.ui.theme.CustomOrange
import com.example.project01_danp.ui.theme.CustomViolet

@Composable
fun JoinScreen(navController: NavHostController) {
    val mContext = LocalContext.current


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomOrange.copy(0.25f))
                .padding(top = 120.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,


        ) {

            Image(
                painter = painterResource(id = R.drawable.alcancia_app),
                contentDescription = null,
                Modifier
                    .width(100.dp)
                    .padding(bottom = 24.dp, top = 24.dp)
            )
            val inputCodeState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = inputCodeState.value,
                onValueChange = { inputCodeState.value = it },
                placeholder = {
                    Text(text = "Código de Alcancía",
                        // color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( bottom = 8.dp),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center
                )
            )

            Button(
                onClick = {
                    mContext.startActivity(Intent(mContext, MainActivity::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp).height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = CustomViolet)
            ) {
                Text(text = "UNIRME")
            }
        }
    }


