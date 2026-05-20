package com.example.applicationrestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// IMPORTS CRUCIAUX : Ils permettent à MainActivity de voir tes autres écrans
import com.example.applicationrestaurant.ui.LoginScreen
import com.example.applicationrestaurant.ui.MainAppContent
import com.example.applicationrestaurant.ui.theme.ApplicationRestaurantTheme
import com.example.applicationrestaurant.viewmodel.RestaurantViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationRestaurantTheme {
                val navController = rememberNavController()
                val viewModel: RestaurantViewModel = viewModel()
                var isLoggedIn by remember { mutableStateOf(false) }

                NavHost(
                    navController = navController,
                    startDestination = if (isLoggedIn) "main" else "login"
                ) {
                    composable("login") {
                        LoginScreen(onLoginSuccess = {
                            isLoggedIn = true
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        })
                    }
                    composable("main") {
                        MainAppContent(viewModel)
                    }
                }
            }
        }
    }
}