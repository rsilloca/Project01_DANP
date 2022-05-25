package com.example.project01_danp.navigation

import com.example.project01_danp.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String) {
    object Home : BottomNavItem("Inicio", R.drawable.ic_home,"home")
    object Logout: BottomNavItem("Salir",R.drawable.ic_exit,"logout")
}
