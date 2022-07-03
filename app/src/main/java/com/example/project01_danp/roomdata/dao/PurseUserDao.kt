package com.example.project01_danp.roomdata.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.project01_danp.roomdata.model.PurseUser

@Dao
abstract class PurseUserDao {
    @Query("SELECT * FROM purse_user WHERE user_id= :idUser")
    abstract fun getPurseUserByUser(idUser: String): PurseUser?
    @Query("SELECT * FROM purse_user WHERE purse_id= :idPurse")
    abstract fun getPurseUserByPurse(idPurse: String): PurseUser?
}