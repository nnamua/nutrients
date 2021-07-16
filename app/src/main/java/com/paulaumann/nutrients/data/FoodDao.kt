package com.paulaumann.nutrients.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class FoodDao {

    @Query("SELECT * FROM food WHERE id IS :id")
    abstract fun getFoodById(id: Int): Flow<Food?>

    @Query("SELECT * FROM food WHERE category LIKE '%' || :category || '%' LIMIT :limit")
    abstract fun getFoodByCategory(category: String, limit: Int): Flow<List<Food>>

    @Query("SELECT * FROM food WHERE name LIKE '%' || :partialName || '%' LIMIT :limit")
    abstract fun getFoodByPartialName(partialName: String, limit: Int): Flow<List<Food>>

    @Transaction
    @Query("SELECT * FROM plan_entries WHERE date > :after AND date < :before")
    abstract fun getConsumedFood(after: Date, before: Date): Flow<List<ConsumedFood>>

}