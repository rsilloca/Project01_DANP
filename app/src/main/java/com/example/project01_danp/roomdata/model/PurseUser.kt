package com.example.project01_danp.roomdata.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purse_user")
data class PurseUser(
    @PrimaryKey (autoGenerate = false)
    val id: String,
    val user_id: String,
    val purse_id: String
) {
    override fun toString(): String {
        return "Purse User ($user_id, $purse_id)"
    }
}