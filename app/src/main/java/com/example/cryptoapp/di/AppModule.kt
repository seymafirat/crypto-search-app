package com.example.cryptoapp.di

import com.example.cryptoapp.feature.data.remote.CryptoApi
import com.example.cryptoapp.feature.domain.repository.CryptoRepository
import com.example.cryptoapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCryptoRepository(
        api: CryptoApi
    ) = CryptoRepository(api)

    @Singleton
    @Provides
    fun provideCryptoApi(): CryptoApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CryptoApi::class.java)
    }
}