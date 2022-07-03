package com.example.project01_danp.roomdata.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.project01_danp.roomdata.model.Purse

@Dao
abstract class PurseDao {
    @Query("SELECT * FROM purse")
    abstract fun getAllPurses(): LiveData<List<Purse>>

    @Query("SELECT * FROM purse WHERE id = :id")
    abstract fun getPurseById(id: String): Purse?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPurse(purse: Purse)

    @Delete
    abstract fun deletePurse(purse: Purse)

    @Update
    abstract fun updatePurse(purse: Purse)

    @Query("SELECT * FROM purse WHERE code = :code")
    abstract fun getPursesByCode(code:String): Purse?
}