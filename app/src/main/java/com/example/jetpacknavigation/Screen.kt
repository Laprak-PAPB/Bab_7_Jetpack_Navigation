package com.example.jetpacknavigation

sealed class Screen(val route: String) {
    object Greeting : Screen("greeting")
    object Login : Screen("login")
    object RekomendasiTempat : Screen("rekomendasi_tempat")
}

