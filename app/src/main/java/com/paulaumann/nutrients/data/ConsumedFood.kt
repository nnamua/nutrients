package com.paulaumann.nutrients.data

import androidx.room.Embedded
import androidx.room.Relation

data class ConsumedFood (
    @Embedded
    val consumed: Consumed,

    @Relation(parentColumn = "food_id", entityColumn = "id")
    val food: Food
) {

    override operator fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as ConsumedFood
        return consumed.consumedId == other.consumed.consumedId
    }

}