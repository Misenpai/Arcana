package com.misenpai.arcana.di

import android.content.Context
import android.content.SharedPreferences
import com.misenpai.arcana.data.api.ArcanaApi
import com.misenpai.arcana.data.datasource.ArcanaDataSource
import com.misenpai.arcana.data.repository.ArcanaRepository as ArcanaRepositoryImpl // Alias to avoid confusion
import com.misenpai.arcana.domain.repository.ArcanaRepository
import com.misenpai.arcana.domain.usecase.GetArcanaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideArcanaRepository(dataSource: ArcanaDataSource): ArcanaRepository =
        ArcanaRepositoryImpl(dataSource) // Use the concrete implementation

    @Provides
    @Singleton
    fun provideGetArcanaUseCase(repository: ArcanaRepository): GetArcanaUseCase =
        GetArcanaUseCase(repository)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("arcana_prefs", Context.MODE_PRIVATE)
    }
}