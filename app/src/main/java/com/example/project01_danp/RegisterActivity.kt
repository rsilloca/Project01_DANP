package com.example.project01_danp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project01_danp.firebase.models.User
import com.example.project01_danp.firebase.repository.UserRepository
import com.example.project01_danp.firebase.service.AuthService
import com.example.project01_danp.ui.theme.CustomGreen
import com.example.project01_danp.ui.theme.Project01_DANPTheme
import com.example.project01_danp.ui.theme.fontPacifico
import com.example.project01_danp.utils.connectionStatus
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class RegisterActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project01_DANPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BuildContentRegister()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun BuildContentRegister() {
    val mContext = LocalContext.current
    Scaffold {
        Column {

            Box {
                Image(
                    painter = painterResource(id = R.drawable.bg_header_app),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 34.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Colaboremos",
                        fontFamily = fontPacifico,
                        color = Color.White,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.rotate(-5f)
                    )
                    Row {
                        Text(
                            text = "PE",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Image(
                            painter = painterResource(id = R.drawable.peru_flag),
                            contentDescription = null,
                            modifier = Modifier
                                .height(28.dp)
                                .padding(top = 8.dp, start = 8.dp)
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.alcancia_app),
                    contentDescription = null,
                    Modifier
                        .width(120.dp)
                        .padding(bottom = 24.dp)
                )

                val inputNameState = remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = inputNameState.value,
                    onValueChange = { inputNameState.value = it },
                    label = { Text(text = mContext.getString(R.string.txt_input_nombre_apellidos )) },
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
                val inputPhoneState = remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = inputPhoneState.value,
                    onValueChange = { inputPhoneState.value = it },
                    label = { Text(text = mContext.getString(R.string.txt_input_correo_electronico )) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                val inputPwdState = remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = inputPwdState.value,
                    onValueChange = { inputPwdState.value = it },
                    label = { Text(text = mContext.getString(R.string.txt_input_clave)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        if (!connectionStatus(mContext)) {
                            Toast.makeText(
                                mContext,
                                "No connection to internet",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else if (inputNameState.value.text.isEmpty() || inputPhoneState.value.text.isEmpty() ||
                            inputPwdState.value.text.isEmpty()
                        ) {
                            Toast.makeText(mContext, "No empty fields.", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            register(
                                inputNameState.value.text, inputPhoneState.value.text,
                                inputPwdState.value.text, mContext
                            )
                        }
                    },
                    modifier = Modifier
                        .width(220.dp)
                        .padding(top = 24.dp)
                ) {
                    Text(text = mContext.getString(R.string.txt_btn_registrarse))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .width(220.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Divider(
                        color = CustomGreen,
                        thickness = 1.dp,
                        modifier = Modifier.width(40.dp)
                    )
                    Text(
                        text = mContext.getString(R.string.txt_ya_tienes_cuenta),
                        fontSize = 12.sp,
                        color = CustomGreen
                    )
                    Divider(
                        color = CustomGreen,
                        thickness = 1.dp,
                        modifier = Modifier.width(40.dp)
                    )
                }
                OutlinedButton(
                    onClick = {
                        mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                    },
                    // contentPadding = PaddingValues(horizontal = 48.dp),
                    modifier = Modifier.width(220.dp)
                ) {
                    Text(text = mContext.getString(R.string.txt_btn_inicia_sesion))
                }
            }
        }
    }

}

fun register(fullname: String, email: String, password: String, context: Context) {
    val user = User(
        fullname = fullname,
        email = email
    )
    val auth2: Task<AuthResult> =
        AuthService.firebaseRegister(email, password)
    auth2.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            UserRepository.saveUser(user)
            context.startActivity(Intent(context, MainActivity::class.java))
            Toast.makeText(context, "Successful register", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}