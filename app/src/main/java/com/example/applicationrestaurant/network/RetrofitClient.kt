package com.example.applicationrestaurant.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RetrofitClient est un Singleton (objet unique) qui configure la connexion à l'API.
 * Il sert de "pont" entre le code Android et ton backend Laravel.
 */
object RetrofitClient {
    // Adresse IP spéciale pour l'émulateur Android vers la machine hôte (ton PC)
    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    // L'intercepteur permet de "tracer" les requêtes dans le Logcat pour debugger
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Affiche le contenu complet des requêtes/réponses
    }

    // Client HTTP qui transporte les données
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // instance est l'objet que tu utiliseras pour faire tes appels réseau
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Définit l'URL de base
            .client(httpClient) // Utilise notre client avec logging
            .addConverterFactory(GsonConverterFactory.create()) // Transforme automatiquement le JSON en objets Kotlin
            .build()
            .create(ApiService::class.java) // Crée l'interface qui contient les routes API
    }
}