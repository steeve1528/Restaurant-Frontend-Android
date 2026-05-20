package com.example.applicationrestaurant.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationrestaurant.model.*
import com.example.applicationrestaurant.network.RetrofitClient
import kotlinx.coroutines.launch

class RestaurantViewModel : ViewModel() {

    // --- États ---
    private val _plats = mutableStateOf<List<Plat>>(emptyList())
    val plats: State<List<Plat>> = _plats

    private val _panier = mutableStateListOf<CartItem>()
    val panier: List<CartItem> = _panier

    private val _historique = mutableStateOf<List<Commande>>(emptyList())
    val historique: State<List<Commande>> = _historique

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        fetchPlats()
        fetchHistorique(1)
    }

    // --- Fonctions Réseau ---

    fun fetchPlats() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.getPlats()
                if (response.isSuccessful) {
                    _plats.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                Log.e("API_DEBUG", "Erreur Plats: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchHistorique(userId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getHistoriqueCommandes(userId)
                if (response.isSuccessful) {
                    _historique.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                Log.e("API_DEBUG", "Erreur Historique: ${e.message}")
            }
        }
    }

    // --- Fonctions Panier (Indispensables pour CartScreen) ---

    fun ajouterAuPanier(plat: Plat) {
        val index = _panier.indexOfFirst { it.plat.id == plat.id }
        if (index != -1) {
            val item = _panier[index]
            _panier[index] = item.copy(quantite = item.quantite + 1)
        } else {
            _panier.add(CartItem(plat, 1))
        }
    }

    fun retirerDuPanier(plat: Plat) {
        val index = _panier.indexOfFirst { it.plat.id == plat.id }
        if (index != -1) {
            val item = _panier[index]
            if (item.quantite > 1) {
                _panier[index] = item.copy(quantite = item.quantite - 1)
            } else {
                _panier.removeAt(index)
            }
        }
    }

    fun calculerTotal(): Double {
        return _panier.sumOf { it.plat.prix * it.quantite }
    }

    fun passerCommande(userId: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val total = calculerTotal()
                val request = CommandeRequest(user_id = userId, total = total)
                val response = RetrofitClient.instance.passerCommande(request)

                if (response.isSuccessful) {
                    _panier.clear()
                    fetchHistorique(userId) // Rafraîchir après commande
                    onSuccess()
                }
            } catch (e: Exception) {
                Log.e("API_DEBUG", "Erreur Commande: ${e.message}")
            }
        }
    }
}