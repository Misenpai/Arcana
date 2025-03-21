package com.example.arcana.data.api

import com.example.arcana.data.model.Header
import com.example.arcana.data.model.Language
import com.example.arcana.data.model.Section
import com.example.arcana.data.model.Subheader
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface ArcanaApi {
    @GET("languages/")
    suspend fun getLanguages(): List<Language>

    @GET("languages/{languageId}/sections/")
    suspend fun getSections(@Path("languageId") languageId: Int): List<Section>

    @GET("sections/{sectionId}/headers/")
    suspend fun getHeaders(@Path("sectionId") sectionId: Int): List<Header>

    @GET("headers/{headerId}/subheaders/")
    suspend fun getSubheaders(@Path("headerId") headerId: Int): List<Subheader>

    companion object {
        fun create(): ArcanaApi {
            // Set up HTTP logging interceptor
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Logs full request and response
            }

            // Configure OkHttpClient with logging and timeouts
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(10, TimeUnit.SECONDS) // Timeout for establishing connection
                .readTimeout(10, TimeUnit.SECONDS)    // Timeout for reading response
                .writeTimeout(10, TimeUnit.SECONDS)   // Timeout for sending request
                .build()

            // Build Retrofit instance with the custom client
            val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.29.85/api/") // Your API base URL
                .build()

            return retrofit.create(ArcanaApi::class.java)
        }
    }
}