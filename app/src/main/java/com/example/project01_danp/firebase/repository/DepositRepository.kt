package com.example.project01_danp.firebase.repository

import com.example.project01_danp.firebase.models.Deposit

object DepositRepository: FirebaseRepository<Deposit>(Deposit::class.java){
}