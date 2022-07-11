package com.example.project01_danp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.project01_danp.navigation.BottomNavItem
import org.json.JSONObject
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

@RequiresApi(Build.VERSION_CODES.M)
fun connectionStatus(mContext: Context): Boolean {
    val connectivityManager =
        mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    if (capabilities != null) {
        return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            true
        } else capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }
    return false
}

fun returnTo(navController: NavController){
    navController.navigate(BottomNavItem.Home.screen_route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun sendPushNotification(mContext:Context, name: String, msg: String, quantity: Int, list: List<String>){
    val url = "https://colaboremospe-service.herokuapp.com/send/"
    val json = "{\n" +
            "\"title\": \"Nuevo deposito a la alcancia: $name !!\", \n" +
            "\"msg\": \"$msg \nTotal depositado: S/ $quantity \", \n" +
            "\"tokens\": \n" +
            "$list" +
            "\n}"

    val root = JSONObject(json)

    val requestBody = Volley.newRequestQueue(mContext)

    val postRequest =
        JsonObjectRequest(
            Request.Method.POST, url, root,
            { },
            { error -> Log.e("Error", error.message!!) }
        )

    requestBody.add(postRequest)
}
