package com.example.firebasecompras.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebasecompras.presentation.screens.CartsScreen
import com.example.firebasecompras.presentation.screens.LoginRegisterScreen
import com.example.firebasecompras.presentation.screens.LoginScreen
import com.example.firebasecompras.presentation.screens.MainScreen
import com.example.firebasecompras.presentation.screens.PaymentScreen
import com.example.firebasecompras.presentation.screens.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            LoginRegisterScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("signup") {
            RegisterScreen(navController)
        }
        composable("mainScreen") {
            MainScreen(navController)
        }
        composable("carts") {
            CartsScreen(navController)
        }
        composable(
            route = "pay/{cartId}",
            arguments = listOf(navArgument("cartId") { type = NavType.StringType })
        ) { backStackEntry ->
            val cartId = backStackEntry.arguments?.getString("cartId")
            if (cartId != null) {
                PaymentScreen(navController = navController, cartId = cartId)
            }
        }

    }
}