package com.example.applicationrestaurant.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applicationrestaurant.model.*
import com.example.applicationrestaurant.network.RetrofitClient
import kotlinx.coroutines.launch

class RestaurantViewModel : ViewModel() {
    private val _plats = mutableStateOf<List<Plat>>(emptyList())
    val plats: State<List<Plat>> = _plats

    // Gestion du panier avec CartItem pour les quantités
    private val _panier = mutableStateListOf<CartItem>()
    val panier: List<CartItem> = _panier

    // État pour l'historique des commandes
    private val _historique = mutableStateOf<List<Commande>>(emptyList())
    val historique: State<List<Commande>> = _historique

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        fetchPlats()
        fetchHistorique(1) // On utilise l'ID utilisateur 1 par défaut
    }

    fun fetchPlats() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _plats.value = RetrofitClient.instance.getPlats()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchHistorique(userId: Int) {
        viewModelScope.launch {
            try {
                _historique.value = RetrofitClient.instance.getHistoriqueCommandes(userId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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
                    fetchHistorique(userId) // Rafraîchir l'historique
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
