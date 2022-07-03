package com.example.project01_danp.viewmodel.firebase

import androidx.lifecycle.ViewModel
import com.example.project01_danp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.project01_danp.firebase.models.PurseUserFirebase
import com.example.project01_danp.firebase.repository.PurseUserRepository
import com.google.firebase.firestore.Query

class PurseUserViewModelFirebase:ViewModel() {

    private var purseUserRepository = PurseUserRepository
    private var allLiveDatePurseUserRepository: MultipleDocumentReferenceLiveData<PurseUserFirebase, out Query>? = null

    fun getAllFirebasePurseUserByUserId(idUser: String): MultipleDocumentReferenceLiveData<PurseUserFirebase, out Query>? {
        if(allLiveDatePurseUserRepository == null) {
            allLiveDatePurseUserRepository = purseUserRepository.findPurseUserByUser(idUser)
        }
        return allLiveDatePurseUserRepository
    }

    fun getAllFirebasePurseUserByBoth(idPurse: String, idUser: String): MultipleDocumentReferenceLiveData<PurseUserFirebase, out Query>? {
        if(allLiveDatePurseUserRepository == null) {
            allLiveDatePurseUserRepository = purseUserRepository.findPurseUserByBoth(idPurse, idUser)
        }
        return allLiveDatePurseUserRepository
    }

    fun savePurseUserFirebase(purseUserFirebase: PurseUserFirebase){
        purseUserRepository.save(purseUserFirebase)
    }

}