package com.paulaumann.nutrients.analytics

import com.paulaumann.nutrients.data.ConsumedFood
import com.paulaumann.nutrients.data.Food

open class Analytics() {

    companion object {

        // Calculates the average food nutrients consumed this week
        fun average(entries: List<ConsumedFood>): Food {
            val av = Food.empty()
            if (entries.isEmpty()) return av
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

        // Calculates the summed food nutrients consumed this week
        fun sum(entries: List<ConsumedFood>): Food {
            val sum = Food.empty()
            if (entries.isEmpty()) return sum
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