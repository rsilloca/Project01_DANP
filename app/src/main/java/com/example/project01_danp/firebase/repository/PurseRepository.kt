package com.example.project01_danp.firebase.repository

import com.example.project01_danp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.project01_danp.firebase.models.Purse
import com.google.firebase.firestore.Query

object PurseRepository:FirebaseRepository<Purse>(Purse::class.java){

    fun findByUser(idUser: String) : MultipleDocumentReferenceLiveData<Purse, out Query>? {
        return MultipleDocumentReferenceLiveData(collectionReference.whereEqualTo(
            "user_id", idUser
        ), entityClass)
    }
}