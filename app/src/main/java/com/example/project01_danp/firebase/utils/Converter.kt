package com.example.project01_danp.firebase.utils

import com.example.project01_danp.firebase.models.DepositFirebase
import com.example.project01_danp.firebase.models.PurseFirebase
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.roomdata.model.Purse

fun convertPurse(purse: Purse): PurseFirebase {
    val purseFirebase = PurseFirebase(
        purse.user_id,
        purse.code,
        purse.name,
        purse.description,
        purse.icon_name,
        purse.sub_total
    )
    if(purse.id.isNotEmpty()) {
        purseFirebase.documentId = purse.id
    }
    return purseFirebase
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