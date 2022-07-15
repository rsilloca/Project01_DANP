package com.example.project01_danp.navigation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.project01_danp.firebase.models.User
import com.example.project01_danp.firebase.service.AuthService
import com.example.project01_danp.ui.theme.CustomViolet
import com.example.project01_danp.viewmodel.firebase.UserViewModelFirebase
import com.google.android.gms.tasks.Task


lateinit var user: User
@Composable
fun ProfileScreen(navController: NavHostController) {

    val mContext = LocalContext.current
    val userViewModel = UserViewModelFirebase()

    Column {
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_bg_main),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 34.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = mContext.getString(R.string.txt_mi_perfil),
                    color = Color.White,
                    fontSize = 14.sp, //36 2
                    fontWeight = FontWeight.W800,
                    textAlign = TextAlign.Center,

                    )
                Row {
                    Text(
                        text = user.fullname,
                        color = Color.White,
                        fontSize = 22.sp, //32
                        fontWeight = FontWeight.W700,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .weight(weight = 1f, fill = false)
            .padding(bottom =65.dp)

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,

                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.alcancia_app),

                    contentDescription = null,
                    Modifier
                        .width(120.dp)
                        .padding(bottom = 32.dp, top = 32.dp),
                    alignment = Alignment.Center,
                )

                val inputNameState =
                    remember { mutableStateOf(TextFieldValue(user.fullname)) }
                OutlinedTextField(
                    value = inputNameState.value,
                    onValueChange = { inputNameState.value = it },
                    label = { Text(text = mContext.getString(R.string.txt_input_nombre_apellidos)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                val inputPhoneState = remember { mutableStateOf(TextFieldValue(user.email)) }
                OutlinedTextField(
                    value = inputPhoneState.value,
                    onValueChange = { inputPhoneState.value = it },
                    label = { Text(text = mContext.getString(R.string.txt_input_correo_electronico)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null

                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                )

                Button(
                    onClick = {
                        user.fullname = inputNameState.value.text
                        user.email = inputPhoneState.value.text
                        userViewModel.updateUser(user)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = CustomViolet)
                ) {
                    Text(text = mContext.getString(R.string.txt_btn_guardar_cambios))
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .width(220.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Divider(
                        color = CustomViolet,
                        thickness = 1.dp,

                        modifier = Modifier.width(40.dp)
                    )
                    Text(
                        text = mContext.getString(R.string.txt_actualizar_ccontrasena),
                        fontSize = 12.sp,
                        color = CustomViolet
                    )
                    Divider(
                        color = CustomViolet,
                        thickness = 1.dp,
                        modifier = Modifier.width(40.dp)
                    )
                }


                val inputPwdState = remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = inputPwdState.value,
                    onValueChange = { inputPwdState.value = it },
                    label = { Text(text = mContext.getString(R.string.txt_input_nueva_clave)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                val inputPwdRepeatState = remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = inputPwdRepeatState.value,
                    onValueChange = { inputPwdRepeatState.value = it },
                    label = { Text(text = mContext.getString(R.string.txt_input_repite_clave)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                )


                Button(
                    onClick = {
                        if (inputPwdState.value.text.isNotEmpty() && inputPwdRepeatState.value.text.isNotEmpty()) {
                            val auth: Task<Void> = AuthService.firebaseReauthenticationWithCredential(
                                user.email,
                                inputPwdState.value.text
                            )!!
                            auth.addOnCompleteListener{ task ->
                                if (task.isSuccessful) {
                                    AuthService.firebaseGetCurrentUser()?.updatePassword(inputPwdRepeatState.value.text)
                                    Toast.makeText(mContext, "User Password Updated", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(mContext, "Password Updated Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }else{
                            Toast.makeText(mContext, "Password must not be empty.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = CustomViolet)
                ) {
                    Text(text = mContext.getString(R.string.txt_btn_actualizar_clave))
                }

            }
        }
    }
}