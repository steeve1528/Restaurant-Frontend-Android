package com.example.applicationrestaurant.model

import com.google.gson.annotations.SerializedName

data class Plat(
    @SerializedName("id") val id: Int,
    @SerializedName("nom") val nom: String,
    @SerializedName("description") val description: String?,
    @SerializedName("prix") private val prixJson: String, // Récupère le "12000.00" sous forme de String depuis Laravel
    @SerializedName("disponible") val disponibleInt: Int, // Récupère le 1 ou 0 de MySQL
    @SerializedName("image_url") val imageUrl: String? // Nouveau champ pour l'image
) {
    // Propriété calculée qui convertit la String en Double pour l'interface graphique et les calculs
    val prix: Double
        get() = prixJson.toDoubleOrNull() ?: 0.0

    // Propriété calculée qui transforme le 1 en true (disponible) et le 0 en false (indisponible)
    val disponible: Boolean
        get() = disponibleInt == 1
}

// Structure pour gérer les quantités dans le panier
data class CartItem(
    val plat: Plat,
    val quantite: Int
)
