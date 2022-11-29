package com.neurosky.zonetrainer.data.di

import com.neurosky.zonetrainer.data.remote.api.NeuroService
import com.neurosky.zonetrainer.data.repository.NeuroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://3.38.178.32:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNeuroService(retrofit: Retrofit): NeuroService =
        retrofit.create(NeuroService::class.java)

    @Provides
    @Singleton
    fun provideNeuroRepository(service: NeuroService) = NeuroRepository(service)
}
