package com.example.project01_danp.roomdata.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.project01_danp.roomdata.model.Deposit
//import kotlinx.coroutines.flow.Flow

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
}