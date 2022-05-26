package com.example.project01_danp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project01_danp.firebase.livedata.DocumentReferenceFirebaseLiveData
import com.example.project01_danp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.project01_danp.firebase.models.Purse
import com.example.project01_danp.firebase.repository.PurseRepository
import com.google.firebase.firestore.Query

class PurseViewModel: ViewModel() {

    private var purseRepository = PurseRepository
    private var allLiveDataPurse: MultipleDocumentReferenceLiveData<Purse, out Query>? = null
    private var livePurseRepository: DocumentReferenceFirebaseLiveData<Purse>? = null

    fun getAllPurse(): MultipleDocumentReferenceLiveData<Purse, out Query>? {
        if (allLiveDataPurse == null) {
            allLiveDataPurse = purseRepository.findAll()
        }
        return allLiveDataPurse
    }

    fun getAllPurseByUser(id_user : String):MultipleDocumentReferenceLiveData<Purse, out Query>? {
        if(allLiveDataPurse == null) {
            allLiveDataPurse = purseRepository.findByUser(id_user)
        }
        return allLiveDataPurse
    }

    fun getPurseById(purseId: String): DocumentReferenceFirebaseLiveData<Purse>? {
        livePurseRepository = purseRepository.findById(purseId)
        return livePurseRepository
    }

    fun savePurse(purse : Purse) {
        purseRepository.save(purse)
    }

    fun updatePurse(purse: Purse) {
        purseRepository.update(purse)
    }
}