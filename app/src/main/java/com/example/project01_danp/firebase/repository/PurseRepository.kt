package com.example.project01_danp.firebase.repository

import com.example.project01_danp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.project01_danp.firebase.models.PurseFirebase
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Query

object PurseRepository : FirebaseRepository<PurseFirebase>(PurseFirebase::class.java) {

    fun savePurse(purseFirebase: PurseFirebase, documentId: String){
        this.collectionReference.document(documentId).set(purseFirebase)
    }

    fun findByUser(idUser: String): MultipleDocumentReferenceLiveData<PurseFirebase, out Query> {
        return MultipleDocumentReferenceLiveData(
            collectionReference.whereEqualTo(
                "user_id", idUser
            ), entityClass
        )
    }

    fun getLastPurseCreated(): MultipleDocumentReferenceLiveData<PurseFirebase, out Query> {
        return MultipleDocumentReferenceLiveData(collectionReference.limitToLast(1), entityClass)
    }
}