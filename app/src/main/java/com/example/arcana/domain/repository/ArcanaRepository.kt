package com.example.arcana.domain.repository

import com.example.arcana.domain.model.HeaderDomainModel
import com.example.arcana.domain.model.LanguageDomainModel
import com.example.arcana.domain.model.Resource
import com.example.arcana.domain.model.SectionDomainModel
import com.example.arcana.domain.model.SubheaderDomainModel
import kotlinx.coroutines.flow.Flow

interface ArcanaRepository {
    suspend fun getLanguages(): Flow<Resource<List<LanguageDomainModel>>>
    suspend fun getSections(languageId: Int): Flow<Resource<List<SectionDomainModel>>>
    suspend fun getHeaders(sectionId: Int): Flow<Resource<List<HeaderDomainModel>>>
    suspend fun getSubheaders(headerId: Int): Flow<Resource<List<SubheaderDomainModel>>>
}