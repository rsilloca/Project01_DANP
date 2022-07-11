package com.example.project01_danp.firebase.repository

import com.example.project01_danp.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.project01_danp.firebase.models.DepositFirebase
import com.google.firebase.firestore.Query

object DepositRepository : FirebaseRepository<DepositFirebase>(DepositFirebase::class.java) {

    fun saveDeposit(depositFirebase: DepositFirebase, documentId: String){
        this.collectionReference.document(documentId).set(depositFirebase)
    }

    fun findByPurseId(idPurse: String): MultipleDocumentReferenceLiveData<DepositFirebase, out Query> {
        return MultipleDocumentReferenceLiveData(
            collectionReference.whereEqualTo(
                "purse_id",
                idPurse
            ), entityClass
        )
    }
}