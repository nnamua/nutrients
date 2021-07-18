package com.paulaumann.nutrients.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Room Database singleton object.
 * Builds a database (if it doesn't exist) from assets/nutrients.db file
 */

@Database(entities = [Consumed::class, Food::class], version=1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun consumedDao(): ConsumedDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        /**
         * Returns an instance of this singleton class.
         */
        fun getInstance(
            context: Context
        ): AppDatabase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "nutrients.db"
            ).createFromAsset("nutrients.db").build()
        }
    }

}