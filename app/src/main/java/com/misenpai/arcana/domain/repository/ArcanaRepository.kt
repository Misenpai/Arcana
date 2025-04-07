package com.misenpai.arcana.domain.repository

import com.misenpai.arcana.domain.model.*
import kotlinx.coroutines.flow.Flow

interface ArcanaRepository {
    suspend fun getLanguages(): Flow<Resource<List<LanguageDomainModel>>>
    suspend fun getSections(languageId: Int): Flow<Resource<List<SectionDomainModel>>>
    suspend fun getHeaders(sectionId: Int): Flow<Resource<List<HeaderDomainModel>>>
}