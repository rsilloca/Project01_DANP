package com.example.project01_danp.roomdata.repository

import com.example.project01_danp.roomdata.dao.PurseUserDao
import com.example.project01_danp.roomdata.model.PurseUser

class PurseUserRepository (private val purseUserDao: PurseUserDao) {

    fun getPurseUserByUserId(idUser: String): PurseUser? {
        return purseUserDao.getPurseUserByUser(idUser)
    }

}