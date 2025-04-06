package com.example.arcana.data.api

import com.example.arcana.data.model.Header
import com.example.arcana.data.model.Language
import com.example.arcana.data.model.Section
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

    companion object {
        fun create(): ArcanaApi {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://172.17.0.1/api/")
                .build()
            return retrofit.create(ArcanaApi::class.java)
        }
    }
}