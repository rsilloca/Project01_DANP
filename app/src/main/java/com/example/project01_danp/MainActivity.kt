package com.example.project01_danp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.project01_danp.navigation.*
import com.example.project01_danp.ui.theme.CustomGreen
import com.example.project01_danp.ui.theme.CustomViolet
import com.example.project01_danp.ui.theme.Project01_DANPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Project01_DANPTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BuildContentMain()
                }
            }
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            HomeScreen(navController)
        }
        composable("join") {
            JoinScreen(navController)
        }
        composable("add_purse") {
            AddPurseScreen(navController)
        }
        composable("deposit/{purseJson}") {
            DepositScreen(navController, it.arguments?.getString("purseJson"))
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Logout,
    )
    val mContext = LocalContext.current
    BottomNavigation(
        backgroundColor = CustomGreen,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(
                    text = item.title,
                    fontSize = 9.sp
                ) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.5f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    if (item.screen_route == "logout") {
                        mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                    } else {
                        navController.navigate(item.screen_route) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun BuildContentMain() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    navController.navigate("join") {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                contentColor = Color.White,
                backgroundColor = CustomViolet
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_bubble), contentDescription = "")
            }
        }
    ) {
        NavigationGraph(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Project01_DANPTheme {
        BuildContentMain()
    }
}