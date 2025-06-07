package com.example.exemplosimplesdecompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.exemplosimplesdecompose.view.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            Welcome(navController)
        }
        composable("mainalcgas") {
            AlcoolGasolinaPreco(navController)
        }
        composable("inputView") {
            InputView(navController)
        }
        composable(
            "listaDePostos/{nomeDoPosto}/{lat}/{lgt}",
        ) { backStackEntry ->
            val nomeDoPosto = backStackEntry.arguments?.getString("nomeDoPosto") ?: ""
            val lat = backStackEntry.arguments?.getString("lat") ?: "0.0"
            val lgt = backStackEntry.arguments?.getString("lgt") ?: "0.0"
            ListofGasStations(navController, nomeDoPosto, lat, lgt)
        }
    }
}
