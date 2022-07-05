package com.example.project01_danp.viewmodel.firebase

import androidx.lifecycle.ViewModel
import com.example.project01_danp.firebase.livedata.DocumentReferenceFirebaseLiveData
import com.example.project01_danp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.project01_danp.firebase.models.PurseFirebase
import com.example.project01_danp.firebase.repository.PurseRepository
import com.google.firebase.firestore.Query

class PurseViewModelFirebase: ViewModel() {

    private var purseRepository = PurseRepository
    private var allLiveDataPurseFirebase: MultipleDocumentReferenceLiveData<PurseFirebase, out Query>? = null
    private var livePurseFirebaseRepository: DocumentReferenceFirebaseLiveData<PurseFirebase>? = null

    fun getAllPurse(): MultipleDocumentReferenceLiveData<PurseFirebase, out Query>? {
        if (allLiveDataPurseFirebase == null) {
            allLiveDataPurseFirebase = purseRepository.findAll()
        }
        return allLiveDataPurseFirebase
    }

    fun getAllPurseByUser(id_user : String):MultipleDocumentReferenceLiveData<PurseFirebase, out Query>? {
        if(allLiveDataPurseFirebase == null) {
            allLiveDataPurseFirebase = purseRepository.findByUser(id_user)
        }
        return allLiveDataPurseFirebase
    }

    fun getPurseById(purseId: String): DocumentReferenceFirebaseLiveData<PurseFirebase>? {
        livePurseFirebaseRepository = purseRepository.findById(purseId)
        return livePurseFirebaseRepository
    }

    fun savePurse(purseFirebase : PurseFirebase) {
        purseRepository.save(purseFirebase)
    }

    fun updatePurse(purseFirebase: PurseFirebase) {
        purseRepository.update(purseFirebase)
    }

    fun getLastPurseCreated(): MultipleDocumentReferenceLiveData<PurseFirebase, out Query>?{
        allLiveDataPurseFirebase = purseRepository.getLastPurseCreated()
        return allLiveDataPurseFirebase
    }

    fun deletePurse(purse: PurseFirebase) {
        purseRepository.delete(purse)
    }
}