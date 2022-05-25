package com.example.project01_danp.navigation

import com.example.project01_danp.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String) {
    object Home : BottomNavItem("Home", R.drawable.ic_home,"home")
    object Join: BottomNavItem("Join",R.drawable.ic_bubble,"join")
    object Logout: BottomNavItem("Logout",R.drawable.ic_exit,"logout")
}
