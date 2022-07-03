package com.example.project01_danp.roomdata.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purse")
data class Purse (
    @PrimaryKey (autoGenerate = false)
    val id: String,
    var user_id: String,
    var code: String,
    var name: String,
    var description: String,
    var icon_name: String,
    var sub_total: Int
){
    override fun toString(): String {
        return "Purse (id = $id, user_id = $user_id, code = $code, name = $name, description = $description, icon_name = $icon_name, sub_total = $sub_total)"
    }
}