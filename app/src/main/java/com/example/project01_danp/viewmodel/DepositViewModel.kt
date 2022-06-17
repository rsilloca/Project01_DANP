package com.example.project01_danp.viewmodel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.project01_danp.roomdata.ApplicationDANP
import com.example.project01_danp.roomdata.DatabaseConfig
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.roomdata.repository.DepositRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.project01_danp.paging.DepositsPagingSource
import com.example.project01_danp.services.DepositService

class DepositViewModel(applicationDANP: ApplicationDANP, depositsJSON: String) : AndroidViewModel(applicationDANP) {
    val searchResults = MutableLiveData<List<Deposit>>()
    private val getAllDeposit: LiveData<List<Deposit>>
    private val depositRepository: DepositRepository
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val loadingSpinner = MutableLiveData<Boolean>()

    /* ... */

    private val backendService: DepositService = DepositService(depositsJSON)

    val deposits: Flow<PagingData<Deposit>> = Pager(
            PagingConfig(pageSize = 10, enablePlaceholders = false, prefetchDistance = 3)
        ) {
            DepositsPagingSource(backendService)
        }.flow.cachedIn(viewModelScope)

    init {
        val depositDao = DatabaseConfig.getDatabase(applicationDANP).depositDao()
        depositRepository = DepositRepository(depositDao)
        getAllDeposit = depositRepository.getAllDeposits
    }

    fun insert(deposit: Deposit) = viewModelScope.launch (Dispatchers.IO) { depositRepository.createDeposit(deposit) }

    fun findDeposit(id: Int){
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(id).await()
        }
    }
    private fun asyncFind(id: Int): Deferred<List<Deposit>> = coroutineScope.async (Dispatchers.IO) {
        return@async depositRepository.getAllByPurse(id)
    }

    fun deleteDeposit(deposit: Deposit) = viewModelScope.launch (Dispatchers.IO) { depositRepository.deleteDeposit(deposit) }

    fun deleteByPurse(id_purse: Int) = viewModelScope.launch (Dispatchers.IO) { depositRepository.deleteAllByPurse(id_purse) }

}

class DepositViewModelFactory(private val application: ApplicationDANP, private val depositsJSON: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DepositViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DepositViewModel(application, depositsJSON) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}