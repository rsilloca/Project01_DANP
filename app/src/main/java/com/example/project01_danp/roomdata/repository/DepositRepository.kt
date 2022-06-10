package com.example.project01_danp.roomdata.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.project01_danp.roomdata.dao.DepositDao
import com.example.project01_danp.roomdata.model.Deposit
import kotlinx.coroutines.flow.Flow

class DepositRepository (private val depositDao: DepositDao) {

    val getAllDeposits: LiveData<List<Deposit>> = depositDao.getAllDeposits()

    fun getDepositById(id: Int): Deposit? {
        return depositDao.getDepositById(id)
    }

    fun createDeposit(entity: Deposit) {
        Log.e("TAG", "CREATE")
        return depositDao.insertDeposit(entity)
    }

    fun deleteDeposit(entity: Deposit) {
        Log.e("TAG", "DELETE")
        return depositDao.deleteDeposit(entity)
    }

    fun updateDeposit(entity: Deposit) {
        Log.e("TAG", "UPDATE")
        return depositDao.updateDeposit(entity)
    }

    fun getAllByPurse(purse_id: Int): List<Deposit> {
        return depositDao.getAllByPurse(purse_id)
    }

    fun deleteAllByPurse(purse_id: Int) {
        return depositDao.deleteAllByPurse(purse_id)
    }
}