package com.example.arcana.data.datasource

import com.example.arcana.data.api.ArcanaApi
import com.example.arcana.data.model.Header
import com.example.arcana.data.model.Language
import com.example.arcana.data.model.Section
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArcanaDataSource @Inject constructor(private val api: ArcanaApi) {
    suspend fun getLanguages(): List<Language> = withContext(Dispatchers.IO) {
        api.getLanguages()
    }

    suspend fun getSections(languageId: Int): List<Section> = withContext(Dispatchers.IO) {
        api.getSections(languageId)
    }

    suspend fun getHeaders(sectionId: Int): List<Header> = withContext(Dispatchers.IO) {
        api.getHeaders(sectionId)
    }
}