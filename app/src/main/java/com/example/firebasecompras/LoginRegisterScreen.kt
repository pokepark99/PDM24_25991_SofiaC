package com.example.firebasecompras

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
    }
}

@Composable
fun LoginRegisterScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.navigate("login")
        }) {
            Text("Login")
        }

        Button(onClick = {
            navController.navigate("signup")
        }) {
            Text("SignUp")
        }
    }
}
