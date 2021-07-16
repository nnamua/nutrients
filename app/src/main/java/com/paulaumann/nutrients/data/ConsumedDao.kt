package com.paulaumann.nutrients.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
abstract class ConsumedDao {

    @Insert
    abstract suspend fun addConsumed(consumed: Consumed)

    @Update
    abstract suspend fun updateConsumed(consumed: Consumed)

    @Delete
    abstract suspend fun deleteConsumed(consumed: Consumed)

}