package com.example.cryptoapp.feature.presentation.cryptodetail

import androidx.lifecycle.ViewModel
import com.example.cryptoapp.feature.domain.model.Crypto
import com.example.cryptoapp.feature.domain.repository.CryptoRepository
import com.example.cryptoapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    suspend fun getCrypto(id: String): Resource<Crypto> {
        return repository.getCrypto(id)
    }
}