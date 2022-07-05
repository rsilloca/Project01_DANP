package com.example.project01_danp.viewmodel.room

import androidx.lifecycle.*
import com.example.project01_danp.roomdata.ApplicationDANP
import com.example.project01_danp.roomdata.DatabaseConfig
import com.example.project01_danp.roomdata.model.Purse
import com.example.project01_danp.roomdata.repository.PurseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PurseViewModel (applicationDANP: ApplicationDANP) : AndroidViewModel(applicationDANP) {
    val getAllPurses: LiveData<List<Purse>>
    private val purseRepository: PurseRepository

    init {
        val purseDao = DatabaseConfig.getDatabase(applicationDANP).purseDao()
        purseRepository = PurseRepository(purseDao)
        getAllPurses = purseRepository.getAllPurses
    }

    fun insert(purse: Purse) = viewModelScope.launch (Dispatchers.IO) { purseRepository.createPurse(purse) }
    fun update(purse: Purse) = viewModelScope.launch (Dispatchers.IO) { purseRepository.updatePurse(purse) }

    fun delete(purse: Purse) = viewModelScope.launch (Dispatchers.IO) { purseRepository.deletePurse(purse) }
}

class PurseViewModelFactory(private val application: ApplicationDANP) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PurseViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}