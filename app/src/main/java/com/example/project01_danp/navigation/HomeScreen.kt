package com.example.project01_danp.navigation

import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project01_danp.BuildContentLogin
import com.example.project01_danp.MainActivity
import com.example.project01_danp.R
import com.example.project01_danp.RegisterActivity
import com.example.project01_danp.firebase.models.Purse
import com.example.project01_danp.ui.theme.*


@Composable
fun HomeScreen(navController: NavHostController) {
    var purses = listOf(
        Purse("1",0,"Cumpleaños","Es para Juan'", "sin nombre",20),
        Purse("1",0,"Chanchita","Para un juguete'", "sin nombre",20),
        Purse("1",0,"Chanchita 02","Para un juguete'", "sin nombre",20),
        Purse("1",0,"Cumpleaños 02","Para un juguete'", "sin nombre",20),
        Purse("1",0,"Chanchita 03","Para un juguete'", "sin nombre",20),
        Purse("1",0,"Cumpleaños 03","Para un juguete'", "sin nombre",20),
    )
    val mContext = LocalContext.current
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Icon(imageVector  = Icons.Default.Add , contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "NUEVA ALCANCIA")
            }
            var index = 0
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(purses) { purse ->
                    PurseCard(purse, index, navController)
                    index += 1
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PurseCard(purse: Purse, index: Int, navController: NavHostController){
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 90f else 0f
    )
    Card (
        // elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(16.dp),
        onClick = {
            expandedState = !expandedState
        },
        backgroundColor = when (index % 3) {
            0 -> Color(255, 240, 222)
            1 -> Color(226, 244, 240)
            else -> Color(255, 227, 233)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // .padding(end = 8.dp)
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .alpha(ContentAlpha.medium)
                    .rotate(rotationState)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    contentDescription = "",
                    tint = getTextColor(index)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, end = 12.dp, bottom = 12.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(
                            text = purse.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = getTextColor(index)
                        )
                        Text(
                            text = purse.description,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            color = getTextColor(index)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .border(
                                24.dp,
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.5f)
                            )
                            .padding(all = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "",
                            modifier = Modifier.width(28.dp),
                            tint = Color.Black
                        )
                    }
                }
                if (expandedState) {
                    Text(
                        text = "TOTAL: ${purse.sub_total}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = getTextColor(index),
                        modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                navController.navigate("deposit") {
                                    navController.graph.startDestinationRoute?.let { screen_route ->
                                        popUpTo(screen_route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                        ) {
                            Text(
                                text = "Depositar",
                                color = getTextColor(index)
                            )
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                        ) {
                            Text(
                                text = "Compartir código",
                                color = getTextColor(index)
                            )
                        }
                    }
                }
            }
        }
        /* Row(modifier = Modifier.padding(all = 8.dp)) {
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
            }
        } */
    }
}

fun getTextColor(index: Int): Color {
    return when (index % 3) {
        0 -> Color(222, 188, 149)
        1 -> CustomGreen
        else -> CustomRed
    }
}

