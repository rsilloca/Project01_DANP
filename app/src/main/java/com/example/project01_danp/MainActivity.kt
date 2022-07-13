package com.example.project01_danp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.project01_danp.datastore.DataStoreManager
import com.example.project01_danp.firebase.service.AuthService
import com.example.project01_danp.navigation.*
import com.example.project01_danp.ui.theme.*
import com.example.project01_danp.viewmodel.firebase.PurseUserViewModelFirebase
import com.example.project01_danp.viewmodel.firebase.PurseViewModelFirebase
import com.example.project01_danp.viewmodel.firebase.UserViewModelFirebase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : ComponentActivity() {

    lateinit var dataStoreManager: DataStoreManager
    var fontFamily: String = "Nunito"
    var theme: String = ""
    var language: String = "es"
    var darkTheme: Boolean = false
    var fontFamilyDefault: FontFamily = fontNunito

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userViewModel = UserViewModelFirebase()
        userViewModel.getCurrentUserData()?.observe(this) {
            user = it
        }
        loadPurses()
        // val jsonFileString = getJsonDataFromAsset(applicationContext, "deposits.json")

        dataStoreManager = DataStoreManager(this)

        lifecycleScope.launch {
            dataStoreManager.fontFamily.collect { _font ->
                fontFamily = _font
                fontFamilyDefault = when (_font) {
                    "Roboto" -> fontRoboto
                    "Edu" -> fontEdu
                    "Dancing" -> fontDancing
                    else -> fontNunito
                }
            }
        }
        lifecycleScope.launch {
            dataStoreManager.theme.collect { _theme ->
                theme = _theme
                darkTheme = _theme == "Dark"
            }
        }
        lifecycleScope.launch {
            dataStoreManager.language.collect { _language ->
                language = _language
                var locale = Locale("es")
                if (_language.toLowerCase() == "english") {
                    locale = Locale("en")
                }
                var res = resources
                var displayMetrics = res.displayMetrics
                var conf = res.configuration
                conf.locale = locale
                res.updateConfiguration(conf, displayMetrics)
            }
        }

        setContent {
            Project01_DANPTheme(
                darkTheme = darkTheme,
                fontFamilyDefault
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // if (jsonFileString != null) {
                        BuildContentMain(this) {
                            lifecycleScope.launch {
                                dataStoreManager.setUserEmail("")
                                dataStoreManager.setUserName("")
                                dataStoreManager.setUserPIN("")
                            }
                            AuthService.firebaseSingOut()
                            Toast.makeText(this, "Sing out successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                    // }
                }
            }
        }
    }

    private fun loadPurses() {
        val purseViewModelFirebase = PurseViewModelFirebase()
        purses2 = mutableListOf()
        purses2.clear()
        val purseUserViewModelFirebase = PurseUserViewModelFirebase()
        val uid = AuthService.firebaseGetCurrentUser()?.uid
        if(uid != null) {
            purseUserViewModelFirebase.getAllFirebasePurseUserByUserId(uid)?.observe(this) { data ->
                purses2.clear()
                data!!.forEach{ purses ->
                    purseViewModelFirebase.getPurseById(purses.purse_id)?.observe(this) {
                        purses2 += it
                    }
                }
            }
        }
    }

    fun updateCustomizeItems(
        newFontFamily: String,
        newTheme: String,
        newLanguage: String
    ) {
        /* fontFamily = newFontFamily
        theme = newTheme
        language = newLanguage */
        lifecycleScope.launch {
            dataStoreManager.setFontFamily(newFontFamily)
            dataStoreManager.setTheme(newTheme)
            dataStoreManager.setLanguage(newLanguage)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun NavigationGraph(navController: NavHostController, activity: MainActivity) {
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
        composable("list_deposits/{purseJson}") {
            Deposits(navController, it.arguments?.getString("purseJson"))
        }
        composable("deposit/{purseJson}") {
            DepositScreen(navController, it.arguments?.getString("purseJson"))
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable("customize") {
            CustomizeScreen(navController = navController, activity.fontFamily, activity.theme, activity.language) {
                fontFamily: String, theme: String, language: String ->
                activity.updateCustomizeItems(fontFamily, theme, language)
            }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController, logout: () -> Unit) {
    val itemsFirst = listOf(
        BottomNavItem.Home,
        BottomNavItem.Profile
    )
    val itemsSecond = listOf(
        BottomNavItem.Customization,
        BottomNavItem.Logout
    )
    val mContext = LocalContext.current
    BottomNavigation(
        backgroundColor = CustomGreen,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        itemsFirst.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title,
                        Modifier.width(24.dp)
                    )},
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
                        logout()
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
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            },
            label = { Text(text = "") },
            onClick = {},
            unselectedContentColor = Color.Transparent,
            selected = false
        )
        itemsSecond.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title,
                        Modifier.width(24.dp)
                    )},
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
                        logout()
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

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun BuildContentMain(activity: MainActivity, logout: () -> Unit) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController) {
                logout()
            }
        },
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
        NavigationGraph(navController = navController, activity)
    }
}

/* @RequiresApi(Build.VERSION_CODES.M)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Project01_DANPTheme {
        BuildContentMain()
    }
} */