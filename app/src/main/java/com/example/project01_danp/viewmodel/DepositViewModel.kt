package com.example.project01_danp.viewmodel

import androidx.lifecycle.*
import com.example.project01_danp.roomdata.ApplicationDANP
import com.example.project01_danp.roomdata.DatabaseConfig
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.roomdata.repository.DepositRepository
import kotlinx.coroutines.*

class DepositViewModel (applicationDANP: ApplicationDANP) : AndroidViewModel(applicationDANP) {
    val searchResults = MutableLiveData<List<Deposit>>()
    val getAllDeposit: LiveData<List<Deposit>>
    private val depositRepository: DepositRepository
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

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

class DepositViewModelFactory(private val application: ApplicationDANP) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DepositViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DepositViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}