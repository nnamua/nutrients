package com.paulaumann.nutrients.data

import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This repository provides various functions to retrieve Food objects and ConsumedFood objects
 * from the database.
 * @see Food
 * @see ConsumedFood
 * @see FoodDao
 */

@Singleton
class FoodRepository @Inject constructor(
    private val appDatabase: AppDatabase
){

    fun getFoodById(id: Int): Flow<Food?> {
        return appDatabase.foodDao().getFoodById(id)
    }

    fun getFoodByCategory(category: String, limit: Int): Flow<List<Food>> {
        return appDatabase.foodDao().getFoodByCategory(category, limit)
    }

    fun getFoodByPartialName(partialName: String, limit: Int): Flow<List<Food>> {
        return appDatabase.foodDao().getFoodByPartialName(partialName, limit)
    }

    fun getConsumedFood(after: Date, before: Date): Flow<List<ConsumedFood>> {
        return appDatabase.foodDao().getConsumedFood(after, before)
    }
}