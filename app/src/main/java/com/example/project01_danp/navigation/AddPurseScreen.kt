package com.example.project01_danp.navigation

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.project01_danp.ui.theme.CustomViolet

@Composable
fun AddPurseScreen(navController: NavHostController) {
    val mContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 48.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Tu nueva alcancía está casi lista!",
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
        val inputNameState = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = inputNameState.value,
            onValueChange = { inputNameState.value = it },
            label = { Text(text = "Nombre") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        val inputDescState = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = inputDescState.value,
            onValueChange = { inputDescState.value = it },
            label = { Text(text = "Descripción (Opcional)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 3
        )
        Text(
            text = "Elige un ícono",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(52.dp)
                    .height(52.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = CustomGray)
            ) {
               Icon(
                   painter = painterResource(id = R.drawable.ic_celebration),
                   contentDescription = "",
                   tint = Color.Black
               )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(52.dp)
                    .height(52.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = CustomGray)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sell),
                    contentDescription = "",
                    modifier = Modifier.width(20.dp),
                    tint = Color.Black
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(52.dp)
                    .height(52.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = CustomGray)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "",
                    modifier = Modifier.width(20.dp),
                    tint = Color.Black
                )
            }
        }
        Button(
            onClick = {
                mContext.startActivity(Intent(mContext, MainActivity::class.java))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp).height(40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = CustomViolet)
        ) {
            Text(text = "CREAR ALCANCÍA")
        }
    }
}