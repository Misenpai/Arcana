package com.example.arcana.data.api

import com.example.arcana.data.model.ArcanaItem
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ArcanaApi {
    @GET("languages/")
    suspend fun getLanguages(): List<ArcanaItem>

    @GET("languages/{languageId}/sections/")
    suspend fun getSections(@Path("languageId") languageId: Int): List<ArcanaItem>

    @GET("sections/{sectionId}/headers/")
    suspend fun getHeaders(@Path("sectionId") sectionId: Int): List<ArcanaItem>

    @GET("headers/{headerId}/subheaders/")
    suspend fun getSubheaders(@Path("headerId") headerId: Int): List<ArcanaItem>

    companion object {
        fun create(): ArcanaApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://arcana/api/") // Update to your actual API URL
                .build()
            return retrofit.create(ArcanaApi::class.java)
        }
    }
}