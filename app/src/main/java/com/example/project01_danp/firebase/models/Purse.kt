package com.example.project01_danp.firebase.models

class Purse(
    var user_id: String,
    var code: Int,
    var name: String,
    var description: String,
    var icon_name: String,
    var sub_total: Int
    ) : FirebaseEntity(documentId = null){
        constructor() : this(
            user_id = "", code = 0, name = "",
                description = "", icon_name = "", sub_total = 0)

    override fun toString(): String {
        return "Purse ($user_id, $code, $name, $description, $icon_name, $sub_total)"
    }
}