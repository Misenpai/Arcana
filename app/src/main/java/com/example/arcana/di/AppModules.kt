package com.example.arcana.di

import com.example.arcana.data.api.ArcanaApi
import com.example.arcana.data.datasource.ArcanaDataSource
import com.example.arcana.data.repository.ArcanaRepository
import com.example.arcana.domain.repository.ArcanaRepository as DomainArcanaRepository
import com.example.arcana.domain.usecase.GetArcanaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideArcanaApi(): ArcanaApi = ArcanaApi.create()

    @Provides
    @Singleton
    fun provideArcanaDataSource(api: ArcanaApi): ArcanaDataSource = ArcanaDataSource(api)

    @Provides
    @Singleton
    fun provideArcanaRepository(dataSource: ArcanaDataSource): DomainArcanaRepository =
        ArcanaRepository(dataSource)

    @Provides
    @Singleton
    fun provideGetArcanaUseCase(repository: DomainArcanaRepository): GetArcanaUseCase =
        GetArcanaUseCase(repository)
}