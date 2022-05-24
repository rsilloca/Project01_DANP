package com.example.project01_danp.firebase.repository

import com.example.project01_danp.firebase.models.Purse

object PurseRepository:FirebaseRepository<Purse>(Purse::class.java){
}