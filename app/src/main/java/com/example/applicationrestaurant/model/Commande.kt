package com.example.applicationrestaurant.model

import com.google.gson.annotations.SerializedName

data class Commande(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("total") val total: Double,
    @SerializedName("statut") val statut: String,
    @SerializedName("created_at") val createdAt: String
)

data class CommandeRequest(
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("total") val total: Double
)