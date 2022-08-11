package com.example.cryptoapp.feature.data.remote

import com.example.cryptoapp.feature.domain.model.Crypto
import com.example.cryptoapp.feature.domain.model.CryptoList
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("ticker")
    suspend fun getCryptoList(
        @Query("key") key: String
    ): CryptoList

    @GET("ticker")
    suspend fun getCrypto(
        @Query("key") key: String,
        @Query("ids") id: String,
        @Query("attributes") attributes: String
    ): Crypto
}