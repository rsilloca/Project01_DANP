package com.example.project01_danp.firebase.models

class DepositFirebase(
    var purse_id: String,
    var user_id: String,
    var quantity: Int,
    var message: String,
) : FirebaseEntity(documentId = null) {
    constructor() : this(purse_id = "", user_id = "", quantity = 0, message = "")

    override fun toString(): String {
        return "Deposit ($purse_id, $user_id, $quantity, $message)"
    }
}