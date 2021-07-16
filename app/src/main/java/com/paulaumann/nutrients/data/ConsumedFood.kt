package com.paulaumann.nutrients.data

import androidx.room.Embedded
import androidx.room.Relation
import com.paulaumann.nutrients.data.Consumed
import com.paulaumann.nutrients.model.Food

data class ConsumedFood (
    @Embedded
    val consumed: Consumed,

    @Relation(parentColumn = "food_id", entityColumn = "id")
    val food: Food
)