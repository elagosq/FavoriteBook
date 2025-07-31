package com.cursokotlin.favoritebook.di

import com.cursokotlin.favoritebook.data.ApiBooks
import com.cursokotlin.favoritebook.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Configurar la inyección de dependencia
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiBooks(retrofit: Retrofit): ApiBooks { // Base url local se le inyecta la interface
        return retrofit.create(ApiBooks::class.java)
    }

}