package com.paulaumann.nutrients.data

import androidx.room.Embedded
import androidx.room.Relation

data class ConsumedFood (
    @Embedded
    val consumed: Consumed,

    @Relation(parentColumn = "food_id", entityColumn = "id")
    val food: Food
)