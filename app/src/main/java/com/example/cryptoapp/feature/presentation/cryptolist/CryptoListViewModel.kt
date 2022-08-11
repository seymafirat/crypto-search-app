package com.example.cryptoapp.feature.presentation.cryptolist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.feature.domain.model.CryptoListItem
import com.example.cryptoapp.feature.domain.repository.CryptoRepository
import com.example.cryptoapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    val cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    val errorMessage = mutableStateOf("")
    val isLoading = mutableStateOf(false)

    private var initialCryptoList =
        listOf<CryptoListItem>() //indirdiğimiz listeyi geçici olarak buraya kaydediyoruz ki arama bırakılırsa geriye verebilelim değeri
    private var isSearchStarting = true

    init {
        loadCryptos()
    }

    fun searchCryptoList(query: String) {
        val listToSearch = if (isSearchStarting) {
            cryptoList.value
        } else {
            initialCryptoList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter { cryptoListItem ->
                cryptoListItem.currency.contains(query.trim(), ignoreCase = true)
            }

            if (isSearchStarting) {
                initialCryptoList = cryptoList.value
                isSearchStarting = false
            }

            cryptoList.value = results
        }
    }

    //dispatchersta default cpu intensive yaptıgımız işlemler için kullanıyoruz

    fun loadCryptos() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCryptoList()
            when (result) {
                is Resource.Success -> {
                    val cryptoItems = result.data?.mapIndexed { index, cryptoListItem ->
                        CryptoListItem(cryptoListItem.currency, cryptoListItem.price)
                    } as List<CryptoListItem>

                    errorMessage.value = ""
                    isLoading.value = false
                    cryptoList.value += cryptoItems
                }

                is Resource.Error -> {
                    errorMessage.value = result.message.toString()
                    isLoading.value = false
                }
            }
        }
    }

    //getCryptoList suspend oldugu ıcın bunu calıstırmak ıcın ya fnk suspend olcaktı yada coroutine scope acıcaktık coroutine scope actık bunun sonucları var



}