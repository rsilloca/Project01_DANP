package com.example.project01_danp.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.project01_danp.roomdata.model.Deposit
import com.example.project01_danp.services.DepositService
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import java.io.IOException
import java.util.*
import kotlin.concurrent.schedule

class DepositsPagingSource(
    private val backendService: DepositService
) : PagingSource<Int, Deposit>() {

    override val keyReuseSupported: Boolean = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Deposit> {
        delay(5000)
        return try {
            val nextPageNumber = params.key ?: 0
            val response = backendService.searchDeposits(nextPageNumber)
            return LoadResult.Page(
                data = response.data ?: listOf(),
                prevKey = response.nextPageNumber - 1,
                nextKey = response.nextPageNumber
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Deposit>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}