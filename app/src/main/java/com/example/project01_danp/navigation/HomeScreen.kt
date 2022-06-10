package com.example.project01_danp.navigation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.project01_danp.R
import com.example.project01_danp.roomdata.model.Purse
import com.example.project01_danp.roomdata.ApplicationDANP
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.ui.theme.*
import com.example.project01_danp.viewmodel.DepositViewModel
import com.example.project01_danp.viewmodel.DepositViewModelFactory
import com.example.project01_danp.viewmodel.PurseViewModel
import com.example.project01_danp.viewmodel.PurseViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(navController: NavHostController) {
    val purses:List<Purse>

    val mContext = LocalContext.current
    val purseViewModel: PurseViewModel = viewModel(
        factory = PurseViewModelFactory(mContext.applicationContext as ApplicationDANP)
    )

//    Log.e("Purses", ""+purseViewModel.getAllPurses.observeAsState(listOf()).value.toString())

    purses = purseViewModel.getAllPurses.observeAsState(listOf()).value

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
                alignment = Alignment.Center,
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
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
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
    val gson = Gson()
    val mContext = LocalContext.current
    val purseViewModel: PurseViewModel = viewModel(
        factory = PurseViewModelFactory(mContext.applicationContext as ApplicationDANP)
    )
    val depositViewModel: DepositViewModel = viewModel(
        factory = DepositViewModelFactory(mContext.applicationContext as ApplicationDANP)
    )


//    lateinit var depositsByPurse: List<Deposit>
    val coroutineScope = rememberCoroutineScope()

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
                        val icon = Icons.Default.Star

                        var painter: Painter? = null
                        if (purse.icon_name == "cell")
                            painter = painterResource(id = R.drawable.ic_sell)
                        else if (purse.icon_name == "celebration")
                            painter = painterResource(id = R.drawable.ic_celebration)

                        if (purse.icon_name == "star") {
                            Icon(
                                imageVector = icon,
                                contentDescription = "",
                                modifier = Modifier.width(28.dp),
                                tint = Color.Black
                            )
                        } else {
                            Icon(
                                painter = painter!!,
                                contentDescription = "",
                                modifier = Modifier.width(20.dp),
                                tint = Color.Black
                            )
                        }

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

                                navController.navigate("deposit".plus("/${gson.toJson(purse)}")) {
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
                            onClick = {
                                if (purse.code.isEmpty()) {
                                    purse.code = purse.user_id + "-" + purse.id
                                    purseViewModel.update(purse)
                                }
                                getClipboard(mContext, purse.code)
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                        ) {
                            Text(
                                text = "Compartir código",
                                color = getTextColor(index)
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            navController.navigate("list_deposits") {
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

                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Depósitos")
                        Icon(painter = painterResource(id = R.drawable.ic_chevron_right),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

fun getClipboard(context: Context, code: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    Toast.makeText(context, "Codigo copiado.", Toast.LENGTH_SHORT).show()
    val clip: ClipData = ClipData.newPlainText("copy text", code)
    clipboard.setPrimaryClip(clip)
}

fun getTextColor(index: Int): Color {
    return when (index % 3) {
        0 -> Color(222, 188, 149)
        1 -> CustomGreen
        else -> CustomRed
    }
}

