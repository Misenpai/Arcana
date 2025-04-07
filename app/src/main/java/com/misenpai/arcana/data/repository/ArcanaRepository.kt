package com.misenpai.arcana.data.repository

import com.misenpai.arcana.data.datasource.ArcanaDataSource
import com.misenpai.arcana.data.model.Header
import com.misenpai.arcana.data.model.Language
import com.misenpai.arcana.data.model.Section
import com.misenpai.arcana.data.model.Subheader
import com.misenpai.arcana.domain.model.*
import com.misenpai.arcana.domain.repository.ArcanaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArcanaRepository @Inject constructor(
    private val dataSource: ArcanaDataSource
) : ArcanaRepository {

    override suspend fun getLanguages(): Flow<Resource<List<LanguageDomainModel>>> = flow {
        emit(Resource.Loading) // Resource<Nothing>, subtype of Resource<List<LanguageDomainModel>>
        try {
            val languages = dataSource.getLanguages()
            val domainModels = languages.map { it.toDomainModel() }
            emit(Resource.Success(domainModels)) // Resource<List<LanguageDomainModel>>
        } catch (e: Exception) {
            emit(Resource.Error(e)) // Resource<Nothing>, subtype of Resource<List<LanguageDomainModel>>
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getSections(languageId: Int): Flow<Resource<List<SectionDomainModel>>> = flow {
        emit(Resource.Loading)
        try {
            val sections = dataSource.getSections(languageId)
            val domainModels = sections.map { it.toDomainModel() }
            emit(Resource.Success(domainModels))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getHeaders(sectionId: Int): Flow<Resource<List<HeaderDomainModel>>> = flow {
        emit(Resource.Loading)
        try {
            val headers = dataSource.getHeaders(sectionId)
            val domainModels = headers.map { it.toDomainModel() }
            emit(Resource.Success(domainModels))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun Language.toDomainModel() = LanguageDomainModel(
        id = id,
        name = name
    )

    private fun Section.toDomainModel() = SectionDomainModel(
        id = id,
        title = title
    )

    private fun Header.toDomainModel() = HeaderDomainModel(
        id = id,
        title = title,
        subheaders = subheaders.map { it.toDomainModel() }
    )

    private fun Subheader.toDomainModel() = SubheaderDomainModel(
        id = id,
        title = title,
        content = content,
        code = code
    )
}