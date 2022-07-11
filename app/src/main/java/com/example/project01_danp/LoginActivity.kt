package com.example.project01_danp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.lifecycleScope
import com.example.project01_danp.datastore.DataStoreManager
import com.example.project01_danp.firebase.service.AuthService
import com.example.project01_danp.navigation.purses2
import com.example.project01_danp.ui.theme.CustomGreen
import com.example.project01_danp.ui.theme.Project01_DANPTheme
import com.example.project01_danp.ui.theme.fontPacifico
import com.example.project01_danp.utils.connectionStatus
import com.example.project01_danp.viewmodel.firebase.PurseUserViewModelFirebase
import com.example.project01_danp.viewmodel.firebase.PurseViewModelFirebase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class LoginActivity : ComponentActivity() {

    private lateinit var dataStoreManager: DataStoreManager

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreManager = DataStoreManager(this)

        lifecycleScope.launch {
            Log.e("ñe?", "Loading lifecycle")
            dataStoreManager.language.collect {
                Log.e("Lamguageeee", it)
                if (it != "") {
                    var locale = Locale(it)
                    var res = resources
                    var displayMetrics = res.displayMetrics
                    var conf = res.configuration
                    conf.locale = locale
                    res.updateConfiguration(conf, displayMetrics)
                }
            }
        }

        lifecycleScope.launch {
            dataStoreManager.userEmail.collect {
                Log.e("Empanada", it)
                if (it != "") {
                    goToMain()
                }
            }
        }

        // var refresh = Intent(this, LoginActivity::class.java)
        // startActivity((refresh))

        setContent {
            Project01_DANPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BuildContentLogin()
                }
            }
        }
    }

    private fun loadPurses(){
        val purseViewModelFirebase = PurseViewModelFirebase()
        purses2 = mutableListOf()
        purses2.clear()
        val purseUserViewModelFirebase = PurseUserViewModelFirebase()
        val uid = AuthService.firebaseGetCurrentUser()?.uid
        purseUserViewModelFirebase.getAllFirebasePurseUserByUserId(uid!!)?.observe(this){ data ->
            purses2.clear()
            data!!.forEach{ purses ->
                purseViewModelFirebase.getPurseById(purses.purse_id)?.observe(this){
                    purses2 += it
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Composable
    fun BuildContentLogin() {
        val mContext = LocalContext.current
        //mContext.getSystemService(LocaleManager.class).setApplicationLocales(newLocaleList(Locale.forLanguageTag("en")))
        //mContext.getSystemService(Locale)

                //val LanContext= LocalContext.current.resources.
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
                    val inputEmailState = remember { mutableStateOf(TextFieldValue()) }
                    OutlinedTextField(
                        value = inputEmailState.value,
                        onValueChange = { inputEmailState.value = it },
                        label = { Text(text = getString(R.string.txt_input_correo_electronico)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                        label = { Text(text = getString(R.string.txt_input_clave )) },
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
                                Toast.makeText(mContext, "No connection to internet", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            else if (inputEmailState.value.text.isEmpty() || inputPwdState.value.text.isEmpty()) {
                                Toast.makeText(mContext, "Empty email or pass", Toast.LENGTH_SHORT)
                                    .show()
//                                mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            } else {
                                login(
                                    inputEmailState.value.text,
                                    inputPwdState.value.text
                                )
                            }
                        },
                        modifier = Modifier
                            .width(220.dp)
                            .padding(top = 24.dp)
                    ) {
                        Text(text = getString(R.string.txt_btn_iniciar_sesion ) )
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
                            text = getString(R.string.txt_no_tienes_cuenta),
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
                            mContext.startActivity(Intent(mContext, RegisterActivity::class.java))
                        },
                        // contentPadding = PaddingValues(horizontal = 48.dp),
                        modifier = Modifier.width(220.dp)
                    ) {
                        Text(text = getString(R.string.txt_btn_registrate ))
                    }
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        val auth: Task<AuthResult> = AuthService.firebaseSingInWithEmail(email, password)
        auth.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                loadPurses()
                Toast.makeText(
                    this, "Authentication successful",
                    Toast.LENGTH_SHORT
                ).show()
                lifecycleScope.launch {
                    dataStoreManager.setUserPIN(password)
                }
                lifecycleScope.launch {
                    dataStoreManager.setUserEmail(email)
                }
                Thread.sleep(1_000)
                goToMain()
            } else {
                Toast.makeText(this, "Wrong email or pass", Toast.LENGTH_SHORT).show()
//            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
            }
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}