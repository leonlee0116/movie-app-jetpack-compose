package com.leondev.movie_app.util.route

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Detail : Screen("MovieDetail")
    object MovieList : Screen("MovieList")
    object Explore : Screen("Explore")
}