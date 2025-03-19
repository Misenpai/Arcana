package com.example.arcana.data.repository

import com.example.arcana.data.datasource.ArcanaDataSource
import com.example.arcana.data.model.ArcanaItem
import com.example.arcana.domain.model.ArcanaDomainModel
import com.example.arcana.domain.model.Resource
import kotlinx.coroutines.Dispatchers
import com.example.arcana.domain.repository.ArcanaRepository as DomainArcanaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArcanaRepository @Inject constructor(private val dataSource: ArcanaDataSource) : DomainArcanaRepository {

    override suspend fun getLanguages(): Flow<Resource<List<ArcanaDomainModel>>> = flow {
        emit(Resource.Loading)
        try {
            val items = dataSource.getLanguages()
            val domainModels = items.map { it.toDomainModel() }
            emit(Resource.Success(domainModels))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getSections(languageId: Int): Flow<Resource<List<ArcanaDomainModel>>> = flow {
        emit(Resource.Loading)
        try {
            val items = dataSource.getSections(languageId)
            val domainModels = items.map { it.toDomainModel() }
            emit(Resource.Success(domainModels))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getHeaders(sectionId: Int): Flow<Resource<List<ArcanaDomainModel>>> = flow {
        emit(Resource.Loading)
        try {
            val items = dataSource.getHeaders(sectionId)
            val domainModels = items.map { it.toDomainModel() }
            emit(Resource.Success(domainModels))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getSubheaders(headerId: Int): Flow<Resource<List<ArcanaDomainModel>>> = flow {
        emit(Resource.Loading)
        try {
            val items = dataSource.getSubheaders(headerId)
            val domainModels = items.map { it.toDomainModel() }
            emit(Resource.Success(domainModels))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun ArcanaItem.toDomainModel(): ArcanaDomainModel {
        return ArcanaDomainModel(
            id = id,
            name = name ?: title ?: "Unknown",
            description = description ?: content,
            sections = sections?.map { section ->
                ArcanaDomainModel.Section(
                    id = section.id,
                    title = section.title,
                    headers = section.headers?.map { header ->
                        ArcanaDomainModel.Section.Header(
                            id = header.id,
                            title = header.title,
                            subheaders = header.subheaders?.map { subheader ->
                                ArcanaDomainModel.Section.Header.Subheader(
                                    id = subheader.id,
                                    title = subheader.title,
                                    content = subheader.content,
                                    code = subheader.code
                                )
                            }
                        )
                    }
                )
            }
        )
    }
}