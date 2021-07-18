package com.paulaumann.nutrients.analytics

import com.paulaumann.nutrients.data.ConsumedFood
import com.paulaumann.nutrients.data.Food

/**
 * The static methods of this class allow reducing list of ConsumedFood into a single object.
 * @see ConsumedFood
 */
open class Analytics() {

    companion object {

        /**
         * Calculates the average food nutrients consumed this week
         * @param entries A list of ConsumedFood objects
         * @return A Food object containing the average values of the entries list
         */
        fun average(entries: List<ConsumedFood>): Food {
            // Sum values, keep track of number, divide by number afterwards
            val av = Food.empty()
            if (entries.isEmpty()) return av
            // Food.keys allows easier access to specific data fields
            for (i in 4 until Food.keys.size) {
                val key = Food.keys[i]
                var valueSum = 0.0
                var numOfValues = 0
                for (entry in entries) {
                    val food = entry.food
                    val foodValue = (food[key] as Number?)?.toDouble() ?: continue
                    val foodMultiplier = entry.consumed.amount / food.getReferenceAmount()
                    valueSum += foodValue * foodMultiplier
                    numOfValues++
                }
                av[key] = valueSum / numOfValues
            }
            return av
        }

        /**
         * Calculates the summed food nutrients consumed this week
         * @param entries A list of ConsumedFood objects
         * @return A Food object containing the summed values of the entries list
         */
        fun sum(entries: List<ConsumedFood>): Food {
            val sum = Food.empty()
            if (entries.isEmpty()) return sum
            // Food.keys allows easier access to specific data fields
            for (i in 4 until Food.keys.size) {
                val key = Food.keys[i]
                var valueSum = 0.0
                for (entry in entries) {
                    val food = entry.food
                    val foodValue = (food[key] as Number?)?.toDouble() ?: continue
                    val foodMultiplier = entry.consumed.amount / food.getReferenceAmount()
                    valueSum += foodValue * foodMultiplier
                }
                sum[key] = valueSum
            }
            return sum
        }
    }
}