package com.example.project01_danp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project01_danp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.project01_danp.firebase.models.Deposit
import com.example.project01_danp.firebase.repository.DepositRepository
import com.google.firebase.firestore.Query

class Prueba(): ViewModel() {
    private var depositRepository = DepositRepository
    private var allLiveDataDeposit: MultipleDocumentReferenceLiveData<Deposit, out Query>?=null

    fun getAllDepositListLiveData(): MultipleDocumentReferenceLiveData<Deposit, out Query>? {
        if(allLiveDataDeposit == null) {
            allLiveDataDeposit = depositRepository.findAll()
        }
        return allLiveDataDeposit
    }

    fun saveDeposit(deposit: Deposit){
        depositRepository.save(deposit)
    }
}