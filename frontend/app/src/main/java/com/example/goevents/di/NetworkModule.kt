package com.example.goevents.di

import com.example.goevents.data.remote.api.GoEventsApiService
import com.example.goevents.data.repository.EventRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGoEventsApiService(): GoEventsApiService {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8085/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoEventsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEventRepository(apiService: GoEventsApiService): EventRepository {
        return EventRepository(apiService)
    }
}
