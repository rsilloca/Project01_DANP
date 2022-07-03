package com.example.project01_danp.roomdata.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deposit")
data class Deposit (
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    var purse_id: String,
    var user_id: String,
    var quantity: Int,
    var message: String,
    var deposit_date: String,
    var user_email: String
) {

    override fun toString(): String {
        return "Deposit(id=$id, purseId=$purse_id, user_id=$user_id, quantity=$quantity, message=$message)"
    }

}