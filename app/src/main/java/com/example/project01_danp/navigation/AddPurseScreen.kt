package com.example.project01_danp.navigation

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
import com.example.project01_danp.R
import com.example.project01_danp.firebase.models.PurseFirebase
import com.example.project01_danp.firebase.models.PurseUserFirebase
import com.example.project01_danp.firebase.service.AuthService
import com.example.project01_danp.firebase.utils.convertPurse
import com.example.project01_danp.firebase.utils.getDocumentIdGenerated
import com.example.project01_danp.roomdata.ApplicationDANP
import com.example.project01_danp.roomdata.model.Purse
import com.example.project01_danp.ui.theme.CustomGray
import com.example.project01_danp.ui.theme.CustomViolet
import com.example.project01_danp.utils.connectionStatus
import com.example.project01_danp.utils.returnTo
import com.example.project01_danp.utils.subscribe
import com.example.project01_danp.viewmodel.firebase.PurseUserViewModelFirebase
import com.example.project01_danp.viewmodel.firebase.PurseViewModelFirebase
import com.example.project01_danp.viewmodel.room.PurseViewModel
import com.example.project01_danp.viewmodel.room.PurseViewModelFactory
import com.google.firebase.messaging.FirebaseMessaging

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
        val inputNameState = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = inputNameState.value,
            onValueChange = { inputNameState.value = it },
            label = { Text(text = mContext.getString(R.string.txt_input_nombre)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        val inputDescState = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = inputDescState.value,
            onValueChange = { inputDescState.value = it },
            label = { Text(text = mContext.getString(R.string.txt_input_descripcion)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 3
        )
        Text(
            text = mContext.getString(R.string.txt_elige_icono),
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
                val id = getDocumentIdGenerated("PurseFirebase")
                val newPurse = Purse(
                    id,
                    auth.firebaseGetCurrentUser()!!.uid,
                    inputNameState.value.text,
                    inputDescState.value.text,
                    icon,
                    0
                )
                if (!connectionStatus(mContext)) {
                    Log.e("TAG", "No internet connection")
                }else{
                    createPurseFirebase(convertPurse(newPurse), id)
                    createLocalPurse(purseViewModel, newPurse)
                    createPurseUser(id)
                    subscribe(mContext, id)
                }
                returnTo(navController)
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

private fun createPurseFirebase(purse: PurseFirebase, documentID: String) {
    val viewModel = PurseViewModelFirebase()
    viewModel.savePurse(purse, documentID)
}

fun createLocalPurse(purseViewModel: PurseViewModel, newPurse: Purse) {
    purseViewModel.insert(newPurse)
}

private fun createPurseUser(idPurse: String) {
    val purseUserViewModelFirebase = PurseUserViewModelFirebase()
    val auth = AuthService
    purseUserViewModelFirebase.savePurseUserFirebase(
        PurseUserFirebase(
            auth.firebaseGetCurrentUser()!!.uid,
            idPurse,
            ""
        )
    )
}