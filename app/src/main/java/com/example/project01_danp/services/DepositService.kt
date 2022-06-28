package com.example.project01_danp.services

import com.example.project01_danp.roomdata.model.Deposit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DepositService(private val depositsJSON: String) {
    fun searchDeposits(nextPageNumber: Int): ResponseDeposit {
        val gson = Gson()
        val listDepositsType = object : TypeToken<List<Deposit>>() {}.type
        val depositsListJSON = gson.fromJson<List<Deposit>>(depositsJSON, listDepositsType)

        var deposits = arrayListOf<Deposit>()
        var deposit: Deposit

        val pageSize = 10

        for (i in 0 until pageSize) {
            deposit = depositsListJSON[i + (nextPageNumber * pageSize)]
            deposits.add(deposit)
        }

        return ResponseDeposit(
            data = deposits,
            nextPageNumber = nextPageNumber + 1,
        )
    }
}

data class ResponseDeposit(val data: List<Deposit>, val nextPageNumber: Int)