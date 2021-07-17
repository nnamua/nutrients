package com.paulaumann.nutrients.data

import androidx.room.*
import java.util.*


@Entity(
    tableName = "plan_entries",
    foreignKeys = [
        ForeignKey(entity = Food::class, parentColumns = ["id"], childColumns = ["food_id"])
    ],
    indices = [Index("food_id")]
)
data class Consumed (
    @ColumnInfo(name = "food_id")   val foodId: Int,
    @ColumnInfo(name = "date")      val date: Date,
    @ColumnInfo(name = "amount")    var amount: Double
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var consumedId: Long = 0

    override operator fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Consumed
        return consumedId == other.consumedId
    }

    override fun hashCode(): Int {
        return foodId
    }
}