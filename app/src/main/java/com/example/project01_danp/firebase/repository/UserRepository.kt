package com.example.project01_danp.firebase.repository

import com.example.project01_danp.firebase.models.UserFirebase
import com.example.project01_danp.firebase.service.AuthService
import com.google.firebase.auth.FirebaseUser

object UserRepository : FirebaseRepository<UserFirebase> (UserFirebase::class.java){

    private lateinit var authUser: FirebaseUser

    fun saveUser(userFirebase: UserFirebase) {
        authUser = AuthService.firebaseGetCurrentUser()!!
        this.saveWithExistentDocumentId(documentId = authUser.uid, UserFirebase(
            userFirebase.fullname,
            userFirebase.email
        ))
    }

    private fun saveWithExistentDocumentId(documentId: String, userFirebase: UserFirebase) =
        this.collectionReference.document(documentId).set(userFirebase)

}