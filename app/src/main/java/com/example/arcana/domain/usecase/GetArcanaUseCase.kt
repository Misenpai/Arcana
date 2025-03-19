package com.example.arcana.domain.usecase

import com.example.arcana.domain.model.ArcanaDomainModel
import com.example.arcana.domain.model.Resource
import com.example.arcana.domain.repository.ArcanaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArcanaUseCase @Inject constructor(
    private val repository: ArcanaRepository
) {
    // Get languages
    suspend fun getLanguages(): Flow<Resource<List<ArcanaDomainModel>>> {
        return repository.getLanguages()
    }

    // Get sections for a specific language
    suspend fun getSections(languageId: Int): Flow<Resource<List<ArcanaDomainModel>>> {
        return repository.getSections(languageId)
    }

    // Get headers for a specific section
    suspend fun getHeaders(sectionId: Int): Flow<Resource<List<ArcanaDomainModel>>> {
        return repository.getHeaders(sectionId)
    }

    // Get subheaders for a specific header
    suspend fun getSubheaders(headerId: Int): Flow<Resource<List<ArcanaDomainModel>>> {
        return repository.getSubheaders(headerId)
    }
}