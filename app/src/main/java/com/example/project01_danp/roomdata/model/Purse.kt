package com.example.project01_danp.roomdata.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purse")
data class Purse (
    @PrimaryKey (autoGenerate = false)
    val documentId: String,
    var user_id: String,
    var name: String,
    var description: String,
    var icon_name: String,
    var sub_total: Int
){
    override fun toString(): String {
        return "Purse (id = $documentId, user_id = $user_id, name = $name, description = $description, icon_name = $icon_name, sub_total = $sub_total)"
    }
}