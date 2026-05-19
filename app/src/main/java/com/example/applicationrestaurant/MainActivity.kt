package com.example.applicationrestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.applicationrestaurant.ui.CartScreen
import com.example.applicationrestaurant.ui.HistoryScreen
import com.example.applicationrestaurant.ui.MenuScreen
import com.example.applicationrestaurant.ui.theme.ApplicationRestaurantTheme
import com.example.applicationrestaurant.viewmodel.RestaurantViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationRestaurantTheme {
                val navController = rememberNavController()
                val viewModel: RestaurantViewModel = viewModel()
                var currentScreen by remember { mutableStateOf("menu") }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
                                label = { Text("Menu") },
                                selected = currentScreen == "menu",
                                onClick = {
                                    currentScreen = "menu"
                                    navController.navigate("menu") {
                                        popUpTo("menu") { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Panier") },
                                label = { Text("Panier") },
                                selected = currentScreen == "cart",
                                onClick = {
                                    currentScreen = "cart"
                                    navController.navigate("cart") {
                                        popUpTo("menu") { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.DateRange, contentDescription = "Historique") },
                                label = { Text("Historique") },
                                selected = currentScreen == "history",
                                onClick = {
                                    currentScreen = "history"
                                    navController.navigate("history") {
                                        popUpTo("menu") { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "menu",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("menu") { MenuScreen(viewModel) }
                        composable("cart") { 
                            CartScreen(viewModel) {
                                currentScreen = "history"
                                navController.navigate("history") {
                                    popUpTo("menu")
                                }
                            } 
                        }
                        composable("history") { HistoryScreen(viewModel) }
                    }
                }
            }
        }
    }
}
