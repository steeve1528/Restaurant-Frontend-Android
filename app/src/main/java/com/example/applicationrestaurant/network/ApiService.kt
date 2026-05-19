package com.example.applicationrestaurant.network

import com.example.applicationrestaurant.model.Commande
import com.example.applicationrestaurant.model.CommandeRequest
import com.example.applicationrestaurant.model.Plat
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("plats")
    suspend fun getPlats(): List<Plat>

    // Modifié pour utiliser Response<Commande> afin que ton ViewModel valide le succès proprement
    @POST("commandes")
    suspend fun passerCommande(@Body request: CommandeRequest): Response<Commande>

    @GET("users/{user_id}/commandes")
    suspend fun getHistoriqueCommandes(@Path("user_id") userId: Int): List<Commande>
}