package com.example.project01_danp.roomdata.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.project01_danp.roomdata.model.Deposit

@Dao
abstract class DepositDao {
    @Query("SELECT * FROM deposit")
    abstract fun getAllDeposits(): LiveData<List<Deposit>>

    @Query("SELECT * FROM deposit WHERE id = :id")
    abstract fun getDepositById(id: Int): Deposit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDeposit(deposit: Deposit)

    @Delete
    abstract fun deleteDeposit(deposit: Deposit)

    @Update
    abstract fun updateDeposit(deposit: Deposit)

    @Query("SELECT * FROM deposit WHERE purse_id = :purse_id")
    abstract fun getAllByPurse(purse_id: Int): List<Deposit>

    @Query("DELETE FROM deposit WHERE purse_id = :purse_id")
    abstract fun deleteAllByPurse(purse_id: Int)
}