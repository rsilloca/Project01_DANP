package com.example.project01_danp.navigation

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project01_danp.BuildContentLogin
import com.example.project01_danp.MainActivity
import com.example.project01_danp.R
import com.example.project01_danp.RegisterActivity
import com.example.project01_danp.firebase.models.Purse
import com.example.project01_danp.ui.theme.CustomGray
import com.example.project01_danp.ui.theme.CustomGreen
import com.example.project01_danp.ui.theme.Project01_DANPTheme
import com.example.project01_danp.ui.theme.fontPacifico



@Composable
fun HomeScreen(navController: NavHostController) {


    lateinit var purses: List<Purse>
    purses=listOf(
        Purse("1",0,"Cumpleaños","Es para Juan'", "sin nombre",20),
        Purse("1",0,"Chanchita","Para un juguete'", "sin nombre",20),

    )

    val mContext = LocalContext.current
    Scaffold {
        Column {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.bg_header_home),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 34.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hola Usuario",
                        color = Color.White,
                        fontSize = 22.sp, //36 2
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,

                    )
                    Row {
                        Text(
                            text = "¡Vamos por un día más de ahorro!",
                            color = Color.White,
                            fontSize = 14.sp, //32
                            textAlign = TextAlign.Center
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
                        .width(80.dp)
                        .padding(bottom = 32.dp, top = 32.dp),
                    alignment =Alignment.Center,
                )



                OutlinedButton(
                    onClick = {
                        navController.navigate("add_purse") {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    // contentPadding = PaddingValues(horizontal = 48.dp),
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 50.dp)
                ) {
                    Icon(imageVector  = Icons.Default.Lock , contentDescription = null)
                    Text(text = "NUEVA ALCANCIA")
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(1) { purse ->
                        AlcanciaCard(navController)
                    }
                }


            }
        }
    }



/*
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Home Screen",
            fontWeight = FontWeight.Bold,
            color = CustomGreen,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Button(onClick = {
            navController.navigate("add_purse") {
                navController.graph.startDestinationRoute?.let { screen_route ->
                    popUpTo(screen_route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Text(text = "Add Purse")
        }
        Button(onClick = {
            navController.navigate("deposit") {
                navController.graph.startDestinationRoute?.let { screen_route ->
                    popUpTo(screen_route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Text(text = "Deposit on Purse")
        }
    }*/
}



@Composable
fun AlcanciaCard (navController: NavHostController){

    val localContext = LocalContext.current
    Card (
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)

    ) {

        Row(modifier = Modifier.padding(all = 8.dp)) {

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

            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Cuota cumpleaños Juan ",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.subtitle1
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_celebration),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colors.primary, CircleShape)
                )

                Button(onClick = {
                    navController.navigate("deposit") {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                    Text(text = "Deposit on Purse")
                }


            }
        }
    }


}

