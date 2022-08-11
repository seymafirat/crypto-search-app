package com.example.cryptoapp.feature.domain.repository

import com.example.cryptoapp.feature.data.remote.CryptoApi
import com.example.cryptoapp.feature.domain.model.Crypto
import com.example.cryptoapp.feature.domain.model.CryptoList
import com.example.cryptoapp.util.Constants.API_KEY
import com.example.cryptoapp.util.Constants.CALL_ATTRIBUTES
import com.example.cryptoapp.util.Resource
import javax.inject.Inject


class CryptoRepository @Inject constructor(
    private val api: CryptoApi
) {

    suspend fun getCryptoList(): Resource<CryptoList> {
        val response = try {
            api.getCryptoList(API_KEY)
        } catch (e: Exception) {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto(id: String): Resource<Crypto> {
        val response = try {
            api.getCrypto(API_KEY, id, CALL_ATTRIBUTES)
        } catch (e: Exception) {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}