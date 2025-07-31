package com.cursokotlin.favoritebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.cursokotlin.favoritebook.screens.DetailBookScreen
import com.cursokotlin.favoritebook.screens.FavoriteScreen
import com.cursokotlin.favoritebook.screens.HomeScreen
import com.cursokotlin.favoritebook.screens.SearchScreen
import com.cursokotlin.favoritebook.screens.SplashScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Splash) {
        composable<Splash> {
            SplashScreen(
                goNavigationHome = {
                    navController.navigate(Home) {
                        popUpTo(Splash) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Home> {
            HomeScreen(
                goNavigationSearch = {
                    navController.navigate(Search)
                },
                goNavigationFavorite = {
                    navController.navigate(Favorite)
                },
                goNavigationDetailBook = { id ->
                    navController.navigate(Detail(id = id))
                }
            )
        }
        composable<Favorite> {
            FavoriteScreen(
                goBack = {
                    navController.popBackStack()
                },
                goNavigationDetailBook = { id ->
                    navController.navigate(Detail(id = id))
                }
            )
        }
        composable<Search> {
            SearchScreen(
                goBack = {
                    navController.popBackStack()
                },
                goNavigationDetailBook = { id ->
                    navController.navigate(Detail(id = id))
                })
        }

        composable<Detail> { navBackStackEntry ->
            val detail: Detail = navBackStackEntry.toRoute()
            DetailBookScreen(id = detail.id) { navController.popBackStack() }
        }
    }
}