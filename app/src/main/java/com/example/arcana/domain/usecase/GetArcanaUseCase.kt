package com.example.arcana.domain.usecase

import com.example.arcana.domain.model.*
import com.example.arcana.domain.repository.ArcanaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArcanaUseCase @Inject constructor(
    private val repository: ArcanaRepository
) {
    suspend fun getLanguages(): Flow<Resource<List<LanguageDomainModel>>> {
        return repository.getLanguages()
    }

    suspend fun getSections(languageId: Int): Flow<Resource<List<SectionDomainModel>>> {
        return repository.getSections(languageId)
    }

    suspend fun getHeaders(sectionId: Int): Flow<Resource<List<HeaderDomainModel>>> {
        return repository.getHeaders(sectionId)
    }
}