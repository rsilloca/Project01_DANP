package com.example.project01_danp.viewmodel.firebase

import androidx.lifecycle.ViewModel
import com.example.project01_danp.firebase.livedata.DocumentReferenceFirebaseLiveData
import com.example.project01_danp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.project01_danp.firebase.models.DepositFirebase
import com.example.project01_danp.firebase.repository.DepositRepository
import com.google.firebase.firestore.Query

class DepositViewModelFirebase: ViewModel() {

    private var depositFirebaseRepository = DepositRepository
    private var allLiveDataDepositFirebase: MultipleDocumentReferenceLiveData<DepositFirebase, out Query>? = null
    private var liveDepositFirebase: DocumentReferenceFirebaseLiveData<DepositFirebase>? = null

    fun getAllDepositsFirebaseByPurseId(idPurse: String): MultipleDocumentReferenceLiveData<DepositFirebase, out Query>? {
        if(allLiveDataDepositFirebase == null) {
            allLiveDataDepositFirebase = depositFirebaseRepository.findByPurseId(idPurse)
        }
        return allLiveDataDepositFirebase
    }

    fun saveDepositFirebase(depositFirebase: DepositFirebase) {
        depositFirebaseRepository.save(depositFirebase)
    }

    fun updateDepositFirebase(depositFirebase: DepositFirebase) {
        depositFirebaseRepository.update(depositFirebase)
    }

    fun deleteAllByPurse(deposits: List<DepositFirebase>) {
        depositFirebaseRepository.deleteAll(deposits)
    }

    fun deleteDeposit(depositFirebase: DepositFirebase){
        depositFirebaseRepository.delete(depositFirebase)
    }
}