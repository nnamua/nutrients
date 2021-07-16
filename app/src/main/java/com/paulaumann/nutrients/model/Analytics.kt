package com.paulaumann.nutrients.model

import com.paulaumann.nutrients.data.ConsumedFood
import com.paulaumann.nutrients.data.FoodDao

open class Analytics() {

    companion object {

        // Calculates the average food nutrients consumed this week
        fun average(entries: List<ConsumedFood>): Food {
            val av = Food.empty()
            if (entries.size == 0) return av
            for (i in 4 until Food.keys.size) {
                val key = Food.keys[i]
                var valueSum = 0.0
                var numOfValues = 0
                for (entry in entries) {
                    val food = entry.food
                    food[key]?.let { valueSum += (it as Number).toDouble(); numOfValues++ }
                }
                av[key] = valueSum / numOfValues
            }
            return av
        }

        // Calculates the summed food nutrients consumed this week
        fun sum(entries: List<ConsumedFood>): Food {
            val sum = Food.empty()
            if (entries.size == 0) return sum
            for (i in 4 until Food.keys.size) {
                val key = Food.keys[i]
                var valueSum = 0.0
                for (entry in entries) {
                    val food = entry.food
                    food[key]?.let { valueSum += (it as Number).toDouble() }
                }
                sum[key] = valueSum
            }
            return sum
        }
    }
}