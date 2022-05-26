package com.example.project01_danp.navigation

import android.content.Intent
import android.util.Log
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project01_danp.MainActivity
import com.example.project01_danp.R
import com.example.project01_danp.firebase.models.Deposit
import com.example.project01_danp.firebase.models.Purse
import com.example.project01_danp.firebase.service.AuthService
import com.example.project01_danp.ui.theme.CustomGray
import com.example.project01_danp.ui.theme.CustomGreen
import com.example.project01_danp.ui.theme.CustomViolet
import com.example.project01_danp.viewmodel.DepositViewModel
import com.example.project01_danp.viewmodel.PurseViewModel

@Composable
fun DepositScreen(navController: NavHostController, id_purse: String?, activity: MainActivity?) {
    val mContext = LocalContext.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 48.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Estas depositando a",
            fontWeight = FontWeight.Bold,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier.width(260.dp)
        )
        Text(
            text = "Cuota cumpleaños ",
            fontWeight = FontWeight.Bold,
            color = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colors.primaryVariant,
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
        TextField(

            value = inputNameState.value,
            onValueChange = { inputNameState.value = it },
            label = { Text(text = "S/",
                      fontSize = 24.sp,
                      textAlign = TextAlign.Left,
                ) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp).padding(bottom = 50.dp),


        )



        val inputMensaState = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = inputMensaState.value,
            onValueChange = { inputMensaState.value = it },
            label = { Text(text = "Mensaje (Opcional)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 3
        )

        Button(
            onClick = {
                var deposit = Deposit()
                deposit = Deposit(
                    "id",
                    id_purse!!,
                    inputNameState.value.text.toInt(),
                    inputMensaState.value.text
                )
                createDeposit(deposit)
//                activity?.updatePurse(id_purse, deposit.quantity)
                mContext.startActivity(Intent(mContext, MainActivity::class.java))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = CustomViolet)
        ) {
            Text(text = "DEPOSITAR")
        }
    }
}
fun createDeposit(deposit: Deposit) {
    val viewModel = DepositViewModel()
    viewModel.saveDeposit(deposit)
}

fun updatePurse(purse: Purse){
    val viewModel = PurseViewModel()
    viewModel.updatePurse(purse)
}