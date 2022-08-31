package com.example.project01_danp.roomdata

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project01_danp.roomdata.dao.DepositDao
import com.example.project01_danp.roomdata.dao.PurseDao
import com.example.project01_danp.roomdata.dao.PurseUserDao
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.roomdata.model.Purse
import com.example.project01_danp.roomdata.model.PurseUser
import com.example.project01_danp.utils.Passphrase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [Deposit::class, Purse::class, PurseUser::class],
    version = 14,
    exportSchema = false
)

abstract class DatabaseConfig : RoomDatabase() {
    // Declaration DAOs
    abstract fun depositDao(): DepositDao
    abstract fun purseDao(): PurseDao
    abstract fun purseUserDao(): PurseUserDao

    // Database Invocation
    companion object {

        private var INSTANCE: DatabaseConfig? = null

        fun getDatabase(context: Context): DatabaseConfig {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    /* instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseConfig::class.java,
                        "word_database.db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance */
                    val builder = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseConfig::class.java,
                        "word_database.db"
                    )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val spName = "sharedPreferencesRoomSqlCypher"
                        val passPhrase = Passphrase(
                            context.applicationContext.getSharedPreferences(spName, Context.MODE_PRIVATE)
                        ).getPassphrase()
                        Log.e("passphrase", passPhrase.toString())
                        val factory = SupportFactory(passPhrase)
                        builder.openHelperFactory(factory)
                    } else {
                        val factory = SupportFactory(SQLiteDatabase.getBytes("DefaultPassPhrase".toCharArray()))
                        builder.openHelperFactory(factory)
                    }
                    instance = builder.fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}