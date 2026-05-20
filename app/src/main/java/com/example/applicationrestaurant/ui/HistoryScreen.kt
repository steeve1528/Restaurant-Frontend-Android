package com.example.applicationrestaurant.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.applicationrestaurant.viewmodel.RestaurantViewModel

@Composable
fun HistoryScreen(viewModel: RestaurantViewModel) {
    val historique = viewModel.historique.value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Historique des commandes", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        if (historique.isEmpty()) {
            Text("Aucune commande trouvée.")
        } else {
            LazyColumn {
                items(historique) { commande ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Commande n°${commande.id}", style = MaterialTheme.typography.titleMedium)
                            Text("Total : ${commande.total} Ar")
                            Text("Statut : ${commande.statut}")
                            Text("Date : ${commande.createdAt}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }

}
