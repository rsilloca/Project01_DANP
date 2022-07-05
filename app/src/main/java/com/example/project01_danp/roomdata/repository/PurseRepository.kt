package com.example.project01_danp.roomdata.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.project01_danp.roomdata.dao.PurseDao
import com.example.project01_danp.roomdata.model.Purse

class PurseRepository (private val purseDao: PurseDao){

    val getAllPurses: LiveData<List<Purse>> = purseDao.getAllPurses()

    fun getPurseById(id: String): Purse? {
        return purseDao.getPurseById(id)
    }

    fun createPurse(purse: Purse) {
        Log.e("TAG", "CREATE")
        return purseDao.insertPurse(purse)
    }

    fun deletePurse(purse: Purse) {
        Log.e("TAG", "DELETE")
        return purseDao.deletePurse(purse)
    }

    fun updatePurse(purse: Purse) {
        Log.e("TAG", "UPDATE")
        return purseDao.updatePurse(purse)
    }

}