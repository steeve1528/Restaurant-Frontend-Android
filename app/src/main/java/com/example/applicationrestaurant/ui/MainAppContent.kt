package com.example.applicationrestaurant.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.applicationrestaurant.viewmodel.RestaurantViewModel

/**
 * MainAppContent agit comme le conteneur principal de l'interface utilisateur après la connexion.
 * Il gère la navigation entre le Menu, le Panier et l'Historique via une barre de navigation inférieure.
 */
@Composable
fun MainAppContent(viewModel: RestaurantViewModel) {
    // Création du contrôleur de navigation pour gérer les transitions entre les écrans
    val navController = rememberNavController()

    // État local pour suivre l'écran actuellement affiché et mettre à jour les icônes de la barre
    var currentScreen by remember { mutableStateOf("menu") }

    // Scaffold fournit la structure de base d'une page Material Design (ici avec une barre en bas)
    Scaffold(
        bottomBar = {
            NavigationBar {
                // Bouton Menu
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, "Menu") },
                    label = { Text("Menu") },
                    selected = currentScreen == "menu", // Met en surbrillance si on est sur cet écran
                    onClick = {
                        currentScreen = "menu"
                        navController.navigate("menu") // Navigue vers le composable "menu"
                    }
                )
                // Bouton Panier
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, "Panier") },
                    label = { Text("Panier") },
                    selected = currentScreen == "cart",
                    onClick = {
                        currentScreen = "cart"
                        navController.navigate("cart")
                    }
                )
                // Bouton Historique
                NavigationBarItem(
                    icon = { Icon(Icons.Default.DateRange, "Historique") },
                    label = { Text("Historique") },
                    selected = currentScreen == "history",
                    onClick = {
                        currentScreen = "history"
                        navController.navigate("history")
                    }
                )
            }
        }
    ) { innerPadding ->
        // NavHost définit les destinations possibles de l'application
        // innerPadding est utilisé pour éviter que le contenu ne soit caché sous la barre de navigation
        NavHost(
            navController = navController,
            startDestination = "menu",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Définition des différentes routes (écrans) et liaison avec le ViewModel
            composable("menu") { MenuScreen(viewModel) }
            composable("cart") {
                // Appel de l'écran Panier avec un callback pour gérer la fin d'une commande
                CartScreen(viewModel, onOrderPlaced = {
                    currentScreen = "history"
                    navController.navigate("history")
                })
            }
            composable("history") { HistoryScreen(viewModel) }
        }
    }
}