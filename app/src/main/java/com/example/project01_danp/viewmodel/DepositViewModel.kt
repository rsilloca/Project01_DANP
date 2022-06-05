package com.example.project01_danp

import androidx.lifecycle.*
import com.example.project01_danp.roomdata.ApplicationDANP
import com.example.project01_danp.roomdata.DatabaseConfig
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.roomdata.repository.DepositRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DepositViewModel (applicationDANP: ApplicationDANP) : AndroidViewModel(applicationDANP) {
    val getAllDeposit: LiveData<List<Deposit>>
    private val repository: DepositRepository

    init {
        val depositDao = DatabaseConfig.getDatabase(applicationDANP).depositDao()
        repository = DepositRepository(depositDao)
        getAllDeposit = repository.getAllDeposits
    }

    fun insert(deposit: Deposit) = viewModelScope.launch (Dispatchers.IO) { repository.createDeposit(deposit) }

}

//class DepositViewModel (private val depositRepository: DepositRepository): ViewModel() {
//
//    val getAllDeposits: LiveData<List<Deposit>> = depositRepository.getAllDeposits
//    fun delete(deposit: Deposit) = viewModelScope.launch { depositRepository.deleteDeposit(deposit) }
//    fun getById(id: Int) = viewModelScope.launch { depositRepository.getDepositById(id) }
//}
class DepositViewModelFactory(private val application: ApplicationDANP) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DepositViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DepositViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}