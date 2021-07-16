package com.paulaumann.nutrients.data

import com.paulaumann.nutrients.model.Food
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

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