package com.example.proyecto_01_danp.firebase.repository

import com.example.proyecto_01_danp.firebase.models.User
import com.example.proyecto_01_danp.firebase.service.AuthService
import com.google.firebase.auth.FirebaseUser

class UserRepository : FirebaseRepository<User> (User::class.java){

    private lateinit var authUser: FirebaseUser

    fun saveUser(user: User) {
        authUser = AuthService.firebaseGetCurrentUser()!!
        this.saveWithExistentDocumentId(documentId = authUser.uid, User(
            user.fullname,
            user.phoneNumber,
            user.pin,
        ))
    }

    private fun saveWithExistentDocumentId(documentId: String, user: User) =
        this.collectionReference.document(documentId).set(user)

}