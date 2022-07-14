package com.example.project01_danp.firebase.utils

import com.example.project01_danp.firebase.models.DepositFirebase
import com.example.project01_danp.firebase.models.PurseFirebase
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.roomdata.model.Purse
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun getDocumentIdGenerated(collection: String):String{
    val db = Firebase.firestore
    val ref: DocumentReference = db.collection(collection).document()
    return ref.id
}

fun convertPurse(purse: Purse): PurseFirebase {
    return PurseFirebase(
        purse.user_id,
        purse.name,
        purse.description,
        purse.icon_name,
        purse.sub_total
    )
}

fun convertPurseFD(purseFirebase: PurseFirebase): Purse {
    val purse = Purse(
        purseFirebase.documentId!!,
        purseFirebase.user_id,
        purseFirebase.name,
        purseFirebase.description,
        purseFirebase.icon_name,
        purseFirebase.sub_total
    )
    return purse
}

fun convertDeposit(deposit: Deposit): DepositFirebase {
    return DepositFirebase(
        deposit.purse_id,
        deposit.user_id,
        deposit.quantity,
        deposit.message,
        deposit.deposit_date,
        deposit.user_email
    )
}

fun convertDepositFD(deposit: DepositFirebase): Deposit {
    return Deposit(
        deposit.documentId!!,
        deposit.purse_id,
        deposit.user_id,
        deposit.quantity,
        deposit.message,
        deposit.deposit_date,
        deposit.user_email
    )
}