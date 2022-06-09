package com.example.project01_danp.viewmodel

import androidx.lifecycle.*
import com.example.project01_danp.roomdata.ApplicationDANP
import com.example.project01_danp.roomdata.DatabaseConfig
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.roomdata.repository.DepositRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DepositViewModel (applicationDANP: ApplicationDANP) : AndroidViewModel(applicationDANP) {
    val getAllDeposit: LiveData<List<Deposit>>
    private val depositRepository: DepositRepository

    init {
        val depositDao = DatabaseConfig.getDatabase(applicationDANP).depositDao()
        depositRepository = DepositRepository(depositDao)
        getAllDeposit = depositRepository.getAllDeposits
    }

    fun insert(deposit: Deposit) = viewModelScope.launch (Dispatchers.IO) { depositRepository.createDeposit(deposit) }

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