package com.example.project01_danp.firebase.repository

import com.example.project01_danp.firebase.livedata.DocumentReferenceFirebaseLiveData
import com.example.project01_danp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.project01_danp.firebase.models.PurseUserFirebase
import com.google.firebase.firestore.Query

object PurseUserRepository: FirebaseRepository<PurseUserFirebase>(PurseUserFirebase::class.java){

    fun findPurseUserByUser(idUser: String) : MultipleDocumentReferenceLiveData<PurseUserFirebase, out Query> {
        return MultipleDocumentReferenceLiveData(collectionReference.whereEqualTo(
            "user_id", idUser
        ), entityClass)
    }

    fun findPurseUserByBoth(idPurse: String, idUser: String) : MultipleDocumentReferenceLiveData<PurseUserFirebase, out Query> {
        return MultipleDocumentReferenceLiveData(collectionReference.whereEqualTo(
            "purse_id", idPurse
        ).whereEqualTo(
            "user_id", idUser
        ), entityClass)
    }

    fun findPurseUserByPurse(idPurse: String) : MultipleDocumentReferenceLiveData<PurseUserFirebase, out Query> {
        return MultipleDocumentReferenceLiveData(collectionReference.whereEqualTo(
            "purse_id", idPurse
        ), entityClass)
    }
}