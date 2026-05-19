package com.example.applicationrestaurant.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.applicationrestaurant.viewmodel.RestaurantViewModel

@Composable
fun HistoryScreen(viewModel: RestaurantViewModel) {
    val historique = viewModel.historique.value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Historique des commandes", style = MaterialTheme.typography.headlineMedium)
        
        Spacer(modifier = Modifier.height(16.dp))

        if (historique.isEmpty()) {
            Text(text = "Aucune commande trouvée.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(historique) { commande ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Commande #${commande.id}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = commande.statut,
                                    color = if (commande.statut == "livré") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Date : ${commande.createdAt}", style = MaterialTheme.typography.bodySmall)
                            Text(
                                text = "Total : ${commande.total} €",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
