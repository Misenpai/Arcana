package com.example.arcana.domain.repository

import com.example.arcana.domain.model.ArcanaDomainModel
import com.example.arcana.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface ArcanaRepository {
    suspend fun getLanguages(): Flow<Resource<List<ArcanaDomainModel>>>
    suspend fun getSections(languageId: Int): Flow<Resource<List<ArcanaDomainModel>>>
    suspend fun getHeaders(sectionId: Int): Flow<Resource<List<ArcanaDomainModel>>>
    suspend fun getSubheaders(headerId: Int): Flow<Resource<List<ArcanaDomainModel>>>
}