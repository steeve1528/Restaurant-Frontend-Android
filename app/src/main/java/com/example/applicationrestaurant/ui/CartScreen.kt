package com.example.applicationrestaurant.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.applicationrestaurant.viewmodel.RestaurantViewModel

@Composable
fun CartScreen(viewModel: RestaurantViewModel, onOrderPlaced: () -> Unit) {
    val panier = viewModel.panier
    val total = viewModel.calculerTotal()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Mon Panier", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(panier) { item ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = item.plat.nom, style = MaterialTheme.typography.bodyLarge)
                        // Affichage du sous-total de la ligne en Ariary (Ar)
                        Text(
                            text = "${item.plat.prix * item.quantite} Ar",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { viewModel.retirerDuPanier(item.plat) }) {
                            Text("-", style = MaterialTheme.typography.headlineMedium)
                        }
                        Text(
                            text = "${item.quantite}",
                            modifier = Modifier.padding(horizontal = 8.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                        IconButton(onClick = { viewModel.ajouterAuPanier(item.plat) }) {
                            Text("+", style = MaterialTheme.typography.headlineMedium)
                        }
                    }
                }
                HorizontalDivider()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total", style = MaterialTheme.typography.headlineSmall)
            // Affichage du Total général en Ariary (Ar)
            Text(
                text = "$total Ar",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.passerCommande(userId = 1, onSuccess = onOrderPlaced) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = panier.isNotEmpty()
        ) {
            Text(text = "Passer la commande", style = MaterialTheme.typography.titleMedium)
        }
    }
}