package com.sahar.aflamy.presentation.router

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sahar.aflamy.presentation.details.MovieDetailsScreen
import com.sahar.aflamy.presentation.list.MoviesListScreen
import okhttp3.internal.immutableListOf

/**
 * This Screen class should have all the possible destinations in App.
 */
sealed class Screen(val route: String) {
    object MoviesListScreen : Screen("List")
    object MoviesDetailsScreen : Screen("Details")

    fun withArgs(vararg args: String): String {
        var parameters = ""
        for (arg in args) {
            parameters += "/{$arg}"
        }
        return route + parameters
    }
}

/**
 * Handle the navigation between app screens
 */
@Composable
fun MoviesAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MoviesListScreen.route) {

        composable(route = Screen.MoviesListScreen.route) {
            MoviesListScreen(navController)
        }
        composable(
            route = Screen.MoviesDetailsScreen.route + "/{movieId}",
            arguments = immutableListOf(navArgument(name = "movieId") {
                type = NavType.StringType
                defaultValue = ""
                nullable = true
            })
        ) {
            MovieDetailsScreen(
                it.arguments?.getString("movieId")?.removeSurrounding("{", "}")
            )
        }
    }
}
