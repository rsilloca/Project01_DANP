package com.example.project01_danp.navigation

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import com.example.project01_danp.firebase.service.AuthService
import com.example.project01_danp.firebase.utils.convertDeposit
import com.example.project01_danp.firebase.utils.convertPurse
import com.example.project01_danp.roomdata.ApplicationDANP
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.roomdata.model.Purse
import com.example.project01_danp.ui.theme.CustomViolet
import com.example.project01_danp.viewmodel.firebase.DepositViewModelFirebase
import com.example.project01_danp.viewmodel.firebase.PurseViewModelFirebase
import com.example.project01_danp.viewmodel.room.DepositViewModel
import com.example.project01_danp.viewmodel.room.DepositViewModelFactory
import com.example.project01_danp.viewmodel.room.PurseViewModel
import com.example.project01_danp.viewmodel.room.PurseViewModelFactory
import com.google.gson.Gson
import java.util.*

@Composable
fun DepositScreen(navController: NavHostController, purseJson: String?) {
    val mContext = LocalContext.current

    val depositViewModel: DepositViewModel = viewModel(
        factory = DepositViewModelFactory(mContext.applicationContext as ApplicationDANP, "")
    )

    val purseViewModel: PurseViewModel = viewModel(
        factory = PurseViewModelFactory(mContext.applicationContext as ApplicationDANP)
    )

    lateinit var purse: Purse
    if (purseJson != null) {
        val gson = Gson()
        purse = gson.fromJson(purseJson, Purse::class.java)
    }

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
            text = purse.name, //purse.name
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
            /* label = { Text(text = "S/",
                      // fontSize = 24.sp,
                      textAlign = TextAlign.Left,
                )}, */
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
                .padding(bottom = 24.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent
            ),
            singleLine = true,
            leadingIcon = {
                Icon(painterResource(id = R.drawable.ic_soles), contentDescription = "")
            },
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            ),
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
                val auth = AuthService

                val newDeposit = Deposit(
                    0,
                    purse.id,
                    auth.firebaseGetCurrentUser()!!.uid,
                    inputNameState.value.text.toInt(),
                    inputMensaState.value.text,
                    Date().toString(),
                    auth.firebaseGetCurrentUser()!!.email ?: "user@gmail.com"
                )

                addDepositFirebase(newDeposit)
                depositViewModel.insert(newDeposit)

                purse.sub_total += inputNameState.value.text.toInt()
                purseViewModel.update(purse)
                updatePurseFirebase(purse)

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
private fun updatePurseFirebase(purse: Purse){
    val purseViewModelFirebase = PurseViewModelFirebase()
    purseViewModelFirebase.updatePurse(
        convertPurse(purse)
    )
}

private fun addDepositFirebase(deposit: Deposit) {
    val viewModel = DepositViewModelFirebase()
    viewModel.saveDepositFirebase(
        convertDeposit(deposit)
    )
}