package com.example.project01_danp.firebase.models

class User(

    var fullname: String,
    var email: String,

    ) : FirebaseEntity(documentId = null) {
    constructor() : this(fullname = "", email = "")

    override fun toString(): String {
        return "User ('$fullname', '$email')"
    }
}