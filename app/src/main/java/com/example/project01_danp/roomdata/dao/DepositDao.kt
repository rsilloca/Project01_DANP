package com.example.project01_danp.roomdata.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.project01_danp.roomdata.model.Deposit

@Dao
abstract class DepositDao {
    @Query("SELECT * FROM deposit")
    abstract fun getAllDeposits(): LiveData<List<Deposit>>

    @Query("SELECT * FROM deposit WHERE documentId = :id")
    abstract fun getDepositById(id: String): Deposit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDeposit(deposit: Deposit)

    @Delete
    abstract fun deleteDeposit(deposit: Deposit)

    @Update
    abstract fun updateDeposit(deposit: Deposit)

    @Query("SELECT * FROM deposit WHERE purse_id = :purse_id")
    abstract fun getAllByPurse(purse_id: String): List<Deposit>

    @Query("DELETE FROM deposit WHERE purse_id = :purse_id")
    abstract fun deleteAllByPurse(purse_id: String)
}