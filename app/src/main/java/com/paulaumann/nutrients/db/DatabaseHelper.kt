package com.paulaumann.nutrients.db

import android.content.Context
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME    = "nutrients.db"
        private lateinit var DB_FILE : File

        // Should be called at app start
        fun init(context: Context){
            DB_FILE = context.getDatabasePath(DB_NAME)
            // Create database if it doesn't exist
            if (!DB_FILE.exists()){
                copyDatabase(context.assets)
            } else {
                Log.d("DatabaseHelper", "Database already exists.")
            }
        }

        fun copyDatabase(assets: AssetManager){
            Log.d("DatabaseHelper", "Copying database...")
            val input  = assets.open(DB_NAME)
            val output = FileOutputStream(DB_FILE)

            val buffer = ByteArray(1024)
            while (input.read(buffer) > 0){
                output.write(buffer)
            }

            output.flush()
            output.close()
            input.close()
            Log.d("DatabaseHelper", "Database copy completed.")
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        return
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        return
    }

    fun verifyDatabase(): Boolean{
        // Check if all necessary tables exist
        val tables = arrayOf("food", "plan_entries", "aliases", "recommended")
        val query = "SELECT name FROM sqlite_master WHERE type = 'table' AND name=?"
        for (table in tables){
            val cursor = readableDatabase.rawQuery(query, arrayOf(table))
            if (cursor == null || cursor.count == 0){
                Log.d("DatabaseHelper", "Database has invalid structure, table '$table' does not exist.")
                return false
            }
            cursor.close()
        }
        return true
    }
}