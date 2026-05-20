package com.example.applicationrestaurant.network

import com.example.applicationrestaurant.model.Plat
import com.example.applicationrestaurant.model.Commande
import com.example.applicationrestaurant.model.CommandeRequest // IMPORTANT : ajoute cet import
import retrofit2.Response // IMPORTANT : ajoute cet import
import retrofit2.http.*

interface ApiService {
    @GET("plats")
    suspend fun getPlats(): Response<List<Plat>>

    @POST("commandes")
    suspend fun passerCommande(@Body request: CommandeRequest): Response<Commande>

    @GET("commandes/user/{userId}")
    suspend fun getHistoriqueCommandes(@Path("userId") userId: Int): Response<List<Commande>>
}