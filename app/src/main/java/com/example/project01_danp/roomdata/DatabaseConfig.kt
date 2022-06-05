package com.example.project01_danp.roomdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project01_danp.roomdata.dao.DepositDao
import com.example.project01_danp.roomdata.dao.PurseDao
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.roomdata.model.Purse

@Database(
    entities = [Deposit::class, Purse::class],
    version = 4,
    exportSchema = false
)

abstract class DatabaseConfig : RoomDatabase() {
    // Declaration DAOs
    abstract fun depositDao(): DepositDao
    abstract fun purseDao(): PurseDao

    // Database Invocation
    companion object {

        private var INSTANCE: DatabaseConfig? = null

        fun getDatabase(context: Context): DatabaseConfig {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseConfig::class.java,
                        "word_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}