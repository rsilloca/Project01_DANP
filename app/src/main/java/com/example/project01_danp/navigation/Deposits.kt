package com.example.project01_danp.navigation

import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.project01_danp.MainActivity
import com.example.project01_danp.R
import com.example.project01_danp.firebase.models.DepositFirebase
import com.example.project01_danp.firebase.models.PurseFirebase
import com.example.project01_danp.firebase.utils.convertDeposit
import com.example.project01_danp.firebase.utils.convertPurse
import com.example.project01_danp.roomdata.ApplicationDANP
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.roomdata.model.Purse
import com.example.project01_danp.utils.connectionStatus
import com.example.project01_danp.viewmodel.firebase.DepositViewModelFirebase
import com.example.project01_danp.viewmodel.firebase.PurseViewModelFirebase
import com.example.project01_danp.viewmodel.room.DepositViewModel
import com.example.project01_danp.viewmodel.room.DepositViewModelFactory
import com.example.project01_danp.viewmodel.room.PurseViewModel
import com.example.project01_danp.viewmodel.room.PurseViewModelFactory
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun Deposits(navController: NavHostController, purseJson: String?) {

    lateinit var purse: Purse
    if (purseJson != null) {
        val gson = Gson()
        purse = gson.fromJson(purseJson, Purse::class.java)
    }

    val deposits: List<Deposit>

    val mContext = LocalContext.current
    val depositViewModel: DepositViewModel = viewModel(
        factory = DepositViewModelFactory(
            mContext.applicationContext as ApplicationDANP
        )
    )
    depositViewModel.findDeposit(purse.documentId)
    deposits = depositViewModel.searchResults.observeAsState(listOf()).value
    // deposits = depositViewModel.getAllPurses.observeAsState(listOf()).value

    // Paging
    val depositListItems: LazyPagingItems<Deposit> =
        depositViewModel.paging(deposits).collectAsLazyPagingItems()
    /*
    val depositListItems=listOf(
        Deposit(1,"1","1",20, "Para la gaseosa","10/06/2022 9:45 PM", "rsilloca@unsa.edu.pe"),
        Deposit(1,"1","1",50, "Para los bocaditos ","10/06/2022 9:45 PM", "mrios@unsa.edu.pe"),
        Deposit(1,"1","1",40, "Para la torta","10/06/2022 9:45 PM", "jfarfanc@unsa.edu.pe"),
        Deposit(1,"1","1",70, "Para la gaseosa","10/06/2022 9:45 PM", "rsilloca@unsa.edu.pe"),
        Deposit(1,"1","1",30, "Para los bocaditos","10/06/2022 9:45 PM", "mrios@unsa.edu.pe"),
        Deposit(1,"1","1",45, "Para la torta","10/06/2022 9:45 PM", "jfarfanc@unsa.edu.pe"),
    )*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 48.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mContext.getString(R.string.txt_estos_son_depositos ),
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

        var index = 0
        val listState = rememberLazyListState()

        /* when (depositViewModel.loadingSpinner.value) {
            true -> CircularProgressIndicator()
            false -> Unit
        } */

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = listState
        ) {
            items(depositListItems) { deposit ->
                if (deposit != null) {
                    Toast.makeText(
                        mContext, "no internet",
                        Toast.LENGTH_SHORT
                    ).show()
                    DepositCard(deposit, index, navController, purse)
                    index += 1
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DepositCard(deposit: Deposit, index: Int, navController: NavHostController, purse: Purse) {
    val mContext = LocalContext.current
    val depositViewModel: DepositViewModel = viewModel(
        factory = DepositViewModelFactory(
            mContext.applicationContext as ApplicationDANP
        )
    )
    val purseViewModel: PurseViewModel = viewModel(
        factory = PurseViewModelFactory(mContext.applicationContext as ApplicationDANP)
    )

    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 90f else 0f
    )
    Card(
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
        backgroundColor = Color(255, 240, 222)
        /* when (index % 3) {
            0 -> Color(255, 240, 222)
            1 -> Color(226, 244, 240)
            else -> Color(255, 227, 233)
        } */
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
                    tint = Color(222, 188, 149) // getTextColor(index)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, end = 12.dp, bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = deposit.user_email,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(222, 188, 149) // getTextColor(index)
                        )
                        Text(
                            text = deposit.deposit_date.toString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            color = Color(222, 188, 149) // getTextColor(index)
                        )
                        Text(
                            text = deposit.message,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            color = Color(222, 188, 149) // getTextColor(index)
                        )
                    }
                    Text(
                        text = "S/ ${deposit.quantity}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(222, 188, 149) // getTextColor(index)
                    )
                }
                if (expandedState) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                        ) {
                            Text(
                                text = "Editar",
                                color = Color(222, 188, 149) // getTextColor(index)
                            )
                        }
                        Button(
                            onClick = {
                                purse.sub_total -= deposit.quantity
                                purseViewModel.update(purse)
                                depositViewModel.deleteDeposit(deposit)
                                mContext.startActivity(Intent(mContext, MainActivity::class.java))
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                        ) {
                            Text(
                                text = "Eliminar",
                                color = Color(222, 188, 149) // getTextColor(index)
                            )
                        }
                    }
                }
            }
        }
    }
}


