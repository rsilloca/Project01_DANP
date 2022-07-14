package com.example.project01_danp.firebase.repository

import com.example.project01_danp.firebase.models.User
import com.example.project01_danp.firebase.service.AuthService
import com.google.firebase.auth.FirebaseUser

object UserRepository : FirebaseRepository<User> (User::class.java){

    private lateinit var authUser: FirebaseUser

    fun saveUser(user: User) {
        authUser = AuthService.firebaseGetCurrentUser()!!
        this.saveWithExistentDocumentId(documentId = authUser.uid, User(
            user.fullname,
            user.email
        ))
    }

    private fun saveWithExistentDocumentId(documentId: String, user: User) =
        this.collectionReference.document(documentId).set(user)

}