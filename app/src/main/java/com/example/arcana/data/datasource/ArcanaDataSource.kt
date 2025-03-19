package com.example.arcana.data.datasource


import com.example.arcana.data.api.ArcanaApi
import com.example.arcana.data.model.ArcanaItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArcanaDataSource @Inject constructor(private val api: ArcanaApi) {
    suspend fun getLanguages(): List<ArcanaItem> = withContext(Dispatchers.IO) {
        api.getLanguages()
    }

    suspend fun getSections(languageId: Int): List<ArcanaItem> = withContext(Dispatchers.IO) {
        api.getSections(languageId)
    }

    suspend fun getHeaders(sectionId: Int): List<ArcanaItem> = withContext(Dispatchers.IO) {
        api.getHeaders(sectionId)
    }

    suspend fun getSubheaders(headerId: Int): List<ArcanaItem> = withContext(Dispatchers.IO) {
        api.getSubheaders(headerId)
    }
}