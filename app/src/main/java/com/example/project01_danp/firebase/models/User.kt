package com.example.project01_danp.firebase.models

class User(

        var fullname: String,
        var phoneNumber: Int,
        var pin: Int

    ) : FirebaseEntity(documentId = null) {
        constructor() : this(fullname = "", phoneNumber = 0, pin = 0)

    override fun toString() :String {
        return "User ('$fullname', '$phoneNumber', '$pin')"
    }
}