package com.example.project01_danp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.project01_danp.firebase.models.Deposit
import com.example.project01_danp.ui.theme.Project01_DANPTheme

class LoginActivity : ComponentActivity() {
    private lateinit var selectDeposit: Prueba
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project01_DANPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    selectDeposit = ViewModelProvider(this).get(Prueba::class.java)
                    val deposit = selectDeposit
                    val deposit2 = Deposit(
                        "1",
                        "1",
                        1,
                        "message"
                    )

                    deposit.saveDeposit(deposit2)
                    deposit.getAllDepositListLiveData()?.observe(this) { deposits ->
                        deposits?.forEach {
                            Log.e("TAG", it.toString())
                        }
                    }


                    Greeting2("AndroidLogin")
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    Project01_DANPTheme {
        Greeting2("AndroidLogin")
    }
}