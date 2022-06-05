package com.example.project01_danp.roomdata

import android.app.Application
import com.example.project01_danp.roomdata.repository.DepositRepository
import com.example.project01_danp.roomdata.repository.PurseRepository

class ApplicationDANP: Application() {
    val database by lazy { DatabaseConfig.getDatabase(this) }
    val repository by lazy { DepositRepository(database.depositDao()) }
    val repositoryPurse by lazy { PurseRepository(database.purseDao()) }
}