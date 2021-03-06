package com.example.project01_danp.firebase.models
import com.google.firebase.firestore.Exclude

abstract class FirebaseEntity(
    @get:Exclude
    var documentId: String ?
)