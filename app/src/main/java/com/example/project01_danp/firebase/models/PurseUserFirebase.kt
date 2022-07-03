package com.example.project01_danp.firebase.models

class PurseUserFirebase (
    var user_id: String,
    var purse_id: String
): FirebaseEntity(documentId = null) {
    constructor(): this(
        user_id = "", purse_id = ""
    )

    override fun toString(): String {
        return "User - Purse ($user_id, $purse_id)"
    }

}