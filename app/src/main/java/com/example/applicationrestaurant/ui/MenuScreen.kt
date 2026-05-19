package com.example.applicationrestaurant.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.applicationrestaurant.model.Plat
import com.example.applicationrestaurant.viewmodel.RestaurantViewModel

@Composable
fun MenuScreen(viewModel: RestaurantViewModel) {
    val plats = viewModel.plats.value
    val isLoading = viewModel.isLoading.value

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(plats) { plat ->
                PlatCard(plat = plat, onAddClick = { viewModel.ajouterAuPanier(plat) })
            }
        }
    }
}

@Composable
fun PlatCard(plat: Plat, onAddClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = plat.nom, style = MaterialTheme.typography.titleLarge)

            plat.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${plat.prix} Ar",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = onAddClick,
                    enabled = plat.disponible // Utilise le Boolean converti depuis Plat.kt
                ) {
                    Text(if (plat.disponible) "Ajouter" else "Indisponible")
                }
            }
        }
    }
}