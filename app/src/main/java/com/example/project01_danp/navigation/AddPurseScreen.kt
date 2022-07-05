package com.example.project01_danp.navigation

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project01_danp.MainActivity
import com.example.project01_danp.R
import com.example.project01_danp.firebase.models.PurseFirebase
import com.example.project01_danp.firebase.service.AuthService
import com.example.project01_danp.firebase.utils.convertPurse
import com.example.project01_danp.roomdata.ApplicationDANP
import com.example.project01_danp.roomdata.model.Purse
import com.example.project01_danp.ui.theme.CustomGray
import com.example.project01_danp.ui.theme.CustomViolet
import com.example.project01_danp.utils.connectionStatus
import com.example.project01_danp.viewmodel.firebase.PurseViewModelFirebase
import com.example.project01_danp.viewmodel.room.PurseViewModel
import com.example.project01_danp.viewmodel.room.PurseViewModelFactory

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun AddPurseScreen(navController: NavHostController) {
    val mContext = LocalContext.current
    val purseViewModel: PurseViewModel = viewModel(
        factory = PurseViewModelFactory(mContext.applicationContext as ApplicationDANP)
    )

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
        var icon = "star"
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Button(
                onClick = {
                    icon = "celebration"
                },
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
                onClick = {
                    icon = "cell"
                },
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
                onClick = {
                    icon = "star"
                },
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
                val auth = AuthService
                // Falta modificar xD
                val newPurse = Purse(
                    auth.firebaseGetCurrentUser()!!.uid,
                    auth.firebaseGetCurrentUser()!!.uid,
                    inputNameState.value.text,
                    inputDescState.value.text,
                    icon,
                    0
                )
                if (!connectionStatus(mContext)) {
                    Log.e("TAG", "No internet connection")
                }else{
                    createPurseFirebase(convertPurse(newPurse))
                    createLocalPurse(purseViewModel, newPurse)
                }
                mContext.startActivity(Intent(mContext, MainActivity::class.java))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = CustomViolet)
        ) {
            Text(text = "CREAR ALCANCÍA")
        }
    }
}

private fun createPurseFirebase(purse: PurseFirebase) {
    val viewModel = PurseViewModelFirebase()
    viewModel.savePurse(purse)
}

fun createLocalPurse(purseViewModel: PurseViewModel, newPurse: Purse) {
    purseViewModel.insert(newPurse)
}
