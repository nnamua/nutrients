package com.paulaumann.nutrients.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This data class models a combination of Food and Consumed objects.
 * It is used to query Consumed entries from the database, aswell as the
 * corresponding Food object.
 * @see Food
 * @see Consumed
 */

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

    override fun hashCode(): Int {
        var result = consumed.hashCode()
        result = 31 * result + food.hashCode()
        return result
    }

}