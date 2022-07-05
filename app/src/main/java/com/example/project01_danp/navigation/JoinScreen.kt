package com.example.project01_danp.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project01_danp.MainActivity
import com.example.project01_danp.R
import com.example.project01_danp.firebase.models.PurseUserFirebase
import com.example.project01_danp.firebase.service.AuthService
import com.example.project01_danp.roomdata.ApplicationDANP
import com.example.project01_danp.roomdata.model.Purse
import com.example.project01_danp.ui.theme.CustomOrange
import com.example.project01_danp.ui.theme.CustomViolet
import com.example.project01_danp.viewmodel.firebase.PurseUserViewModelFirebase
import com.example.project01_danp.viewmodel.firebase.PurseViewModelFirebase
import com.example.project01_danp.viewmodel.room.PurseViewModel
import com.example.project01_danp.viewmodel.room.PurseViewModelFactory

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun JoinScreen(navController: NavHostController) {
    val mContext = LocalContext.current
    val purseViewModel: PurseViewModel = viewModel(
        factory = PurseViewModelFactory(mContext.applicationContext as ApplicationDANP)
    )
    lateinit var actualPurse: Purse


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomOrange.copy(alpha = 0.25f))
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
                Text(
                    text = "Código de Alcancía",
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
                .padding(bottom = 8.dp),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center
            )
        )

        Button(
            onClick = {
                val viewModel = PurseViewModelFirebase()

                viewModel.getPurseByCode(inputCodeState.value.text)?.observeForever {
                    Log.e("TAG", it!![0].toString())
                    actualPurse = Purse(
                        it[0].documentId!!,
                        it[0].user_id,
                        it[0].code,
                        it[0].name,
                        it[0].description,
                        it[0].icon_name,
                        it[0].sub_total
                    )
                    createLocalPurse(purseViewModel, actualPurse)
                    createPurseUser(it[0].documentId!!)
                }
                mContext.startActivity(Intent(mContext, MainActivity::class.java))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = CustomViolet)
        ) {
            Text(text = "UNIRME")
        }
    }

}

private fun createPurseUser(idPurse: String) {
    val purseUserViewModelFirebase = PurseUserViewModelFirebase()
    val auth = AuthService
    purseUserViewModelFirebase.savePurseUserFirebase(
        PurseUserFirebase(
            auth.firebaseGetCurrentUser()!!.uid,
            idPurse
        )
    )
}
