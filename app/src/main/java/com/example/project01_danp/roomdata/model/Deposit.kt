package com.example.project01_danp.roomdata.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deposit")
data class Deposit (
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    var purse_id: Int,
    var user_id: Int,
    var quantity: Int,
    var message: String?
) {
    companion object{
        val AnonymousDeposit by lazy {
            Deposit(
                id = 10000,
                purse_id = 10000,
                user_id = 10000,
                quantity = 10,
                message = "No message"
            )
        }
    }

    override fun toString(): String {
        return "Deposit(id=$id, purseId=$purse_id, user_id=$user_id, quantity=$quantity, message=$message)"
    }

}