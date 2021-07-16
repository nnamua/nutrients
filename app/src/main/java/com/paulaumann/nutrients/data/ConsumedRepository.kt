package com.paulaumann.nutrients.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConsumedRepository @Inject constructor(
    private val appDatabase: AppDatabase
){

    suspend fun addConsumed(consumed: Consumed){
        appDatabase.consumedDao().addConsumed(consumed)
    }

    suspend fun updateConsumed(consumed: Consumed){
        appDatabase.consumedDao().updateConsumed(consumed)
    }

    suspend fun deleteConsumed(consumed: Consumed){
        appDatabase.consumedDao().deleteConsumed(consumed)
    }
}