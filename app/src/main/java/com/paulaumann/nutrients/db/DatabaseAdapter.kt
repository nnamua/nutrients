package com.paulaumann.nutrients.db
/*
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.paulaumann.nutrients.model.Food
import com.paulaumann.nutrients.model.PlanEntry
import java.util.*

class DatabaseAdapter {

    companion object {

        private const val FOOD_TABLE = "food"
        private const val PLAN_TABLE = "plan_entries"

        private var dbHelper: DatabaseHelper? = null



    }
}

class OldDatabaseAdapter(context: Context) {

    companion object {
        private const val FOOD_TABLE = "food"
        private const val PLAN_TABLE = "plan_entries"
    }

    val dbHelper = DatabaseHelper(context)

    /*
        Retrieves a food object from the database
     */
    fun parse(cursor: Cursor): Food {
        // Parses a column into the food data class
        val id = cursor.getInt(0)
        val name = cursor.getString(1)
        val category = cursor.getString(2)
        val reference = cursor.getString(3)
        val energy_kJ = if (cursor.isNull(4)) null else cursor.getInt(4)
        val energy_kcal = if (cursor.isNull(5)) null else cursor.getInt(5)
        val fat_total = if (cursor.isNull(6)) null else cursor.getDouble(6)
        val fatty_acids_saturated = if (cursor.isNull(7)) null else cursor.getDouble(7)
        val fatty_acids_monounsatured = if (cursor.isNull(8)) null else cursor.getDouble(8)
        val fatty_acids_polyunsatured = if (cursor.isNull(9)) null else cursor.getDouble(9)
        val cholesterol = if (cursor.isNull(10)) null else cursor.getDouble(10)
        val carbohydrates = if (cursor.isNull(11)) null else cursor.getDouble(11)
        val sugar = if (cursor.isNull(12)) null else cursor.getDouble(12)
        val starch = if (cursor.isNull(13)) null else cursor.getDouble(13)
        val dietary_fiber = if (cursor.isNull(14)) null else cursor.getDouble(14)
        val proteins = if (cursor.isNull(15)) null else cursor.getDouble(15)
        val salt = if (cursor.isNull(16)) null else cursor.getDouble(16)
        val alcohol = if (cursor.isNull(17)) null else cursor.getDouble(17)
        val water = if (cursor.isNull(18)) null else cursor.getDouble(18)
        val vitamin_a_re = if (cursor.isNull(19)) null else cursor.getInt(19)
        val vitamin_a_rae = if (cursor.isNull(20)) null else cursor.getInt(20)
        val retinol = if (cursor.isNull(21)) null else cursor.getInt(21)
        val beta_carotene_bce = if (cursor.isNull(22)) null else cursor.getInt(22)
        val beta_carotene = if (cursor.isNull(23)) null else cursor.getInt(23)
        val vitamin_b1 = if (cursor.isNull(24)) null else cursor.getDouble(24)
        val vitamin_b2 = if (cursor.isNull(25)) null else cursor.getDouble(25)
        val vitamin_b6 = if (cursor.isNull(26)) null else cursor.getDouble(26)
        val vitamin_b12 = if (cursor.isNull(27)) null else cursor.getDouble(27)
        val niacin = if (cursor.isNull(28)) null else cursor.getDouble(28)
        val folat = if (cursor.isNull(29)) null else cursor.getDouble(29)
        val pantothenic_acid = if (cursor.isNull(30)) null else cursor.getDouble(30)
        val vitamin_c = if (cursor.isNull(31)) null else cursor.getDouble(31)
        val vitamin_d = if (cursor.isNull(32)) null else cursor.getDouble(32)
        val vitamin_e_ate = if (cursor.isNull(33)) null else cursor.getDouble(33)
        val potassium = if (cursor.isNull(34)) null else cursor.getDouble(34)
        val sodium = if (cursor.isNull(35)) null else cursor.getDouble(35)
        val chloride = if (cursor.isNull(36)) null else cursor.getDouble(36)
        val calcium = if (cursor.isNull(37)) null else cursor.getDouble(37)
        val magnesium = if (cursor.isNull(38)) null else cursor.getDouble(38)
        val phosphorus = if (cursor.isNull(39)) null else cursor.getDouble(39)
        val iron = if (cursor.isNull(40)) null else cursor.getDouble(40)
        val iodine = if (cursor.isNull(41)) null else cursor.getDouble(41)
        val zinc = if (cursor.isNull(42)) null else cursor.getDouble(42)
        val selenium = if (cursor.isNull(43)) null else cursor.getDouble(43)
        return Food(id, name, category, reference, energy_kJ, energy_kcal, fat_total, fatty_acids_saturated,
                    fatty_acids_monounsatured, fatty_acids_polyunsatured, cholesterol, carbohydrates,
                    sugar, starch, dietary_fiber, proteins, salt, alcohol, water, vitamin_a_re, vitamin_a_rae,
                    retinol, beta_carotene_bce, beta_carotene, vitamin_b1, vitamin_b2, vitamin_b6, vitamin_b12,
                    niacin, folat, pantothenic_acid, vitamin_c, vitamin_d, vitamin_e_ate, potassium,
                    sodium, chloride, calcium, magnesium, phosphorus, iron, iodine, zinc, selenium)
    }

    /*
        Returns the food object with the given name. If it doesn't exist, return null.
     */
    fun getFoodByName(name: String): Food? {
        val query  = "SELECT * FROM $FOOD_TABLE WHERE name IS ?"
        val cursor = dbHelper.readableDatabase.rawQuery(query, arrayOf(name))
        if (cursor == null) return null;
        var food: Food? = null
        if (cursor.moveToFirst()) food = parse(cursor)
        cursor.close()
        return food
    }

    /*
        Returns the food object with the given id. If it doesn't exist, return null.
     */
    fun getFoodById(id: Int): Food? {
        val query  = "SELECT * FROM $FOOD_TABLE WHERE id IS ?"
        val cursor = dbHelper.readableDatabase.rawQuery(query, arrayOf(id.toString()))
        if (cursor == null) return null;
        var food: Food? = null
        if (cursor.moveToFirst()) food = parse(cursor)
        cursor.close()
        return food
    }

    /*
        Returns a list of food objects in the given category.
     */
    fun getFoodByCategory(category: String, limit: Int): List<Food> {
        val query  = "SELECT * FROM $FOOD_TABLE WHERE category LIKE '%' || ? || '%' LIMIT ?"
        val cursor = dbHelper.readableDatabase.rawQuery(query, arrayOf(category, limit.toString()))
        val entries = mutableListOf<Food>()
        if (cursor != null && cursor.moveToFirst()){
            do {
                val food = parse(cursor)
                entries.add(food)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return entries
    }

    /*
        Returns the food object with the given partial name (for searches). If it doesn't exist, return null.
     */
    fun getFoodByPartialName(partialName: String, limit: Int): List<Food> {
        val query = "SELECT * FROM $FOOD_TABLE WHERE name LIKE '%' || ? || '%' LIMIT ?"
        val cursor = dbHelper.readableDatabase.rawQuery(query, arrayOf(partialName, limit.toString()))
        val entries = mutableListOf<Food>()
        if (cursor != null && cursor.moveToFirst()){
            do {
                val food = parse(cursor)
                entries.add(food)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return entries
    }

    /*
        Stores an entry in the plan database.
     */
    fun storePlanEntry(date: Date, food: Food): PlanEntry {
        val contentValues = ContentValues().apply {
            put("date", date.time)
            put("id", food.id)
            put("amount", food.amount)
        }
        val rowId = dbHelper.writableDatabase.insert(PLAN_TABLE, null, contentValues)
        if (rowId == -1L) Log.d("DBAdapter", "Failed to store daily entry.")
        return PlanEntry(food, rowId)
    }

    /*
        Removes an entry from the plan database
     */
    fun removePlanEntry(entry: PlanEntry){
        val selection = "rowid IS ?"
        val deletedRows = dbHelper.writableDatabase.delete(PLAN_TABLE, selection,
            arrayOf(entry.rowId.toString())
        )
        if (deletedRows != 1) Log.d("DBAdapter", "Error removing plan entry: Expected 1 deleted row, got " + deletedRows)
    }

    /*
        Updates an entry in the plan database
     */
    fun updatePlanEntry(entry: PlanEntry){
        val contentValues = ContentValues().apply {
            put("id", entry.food.id)
            put("amount", entry.food.amount)
        }
        val selection = "rowid IS ?"
        val updatedRows = dbHelper.writableDatabase.update(PLAN_TABLE, contentValues,
            selection, arrayOf(entry.rowId.toString())
        )
        if (updatedRows != 1) Log.d("DBAdapter", "Error updating plan entry: Expected 1 updated row, got " + updatedRows)
    }

    fun getPlanEntries(after: Date, before: Date): List<PlanEntry>{
        val query = "SELECT rowid, id, amount FROM $PLAN_TABLE WHERE ? < date AND ? > date"
        val cursor = dbHelper.readableDatabase.rawQuery(query, arrayOf(after.time.toString(), before.time.toString()))
        val entries = mutableListOf<PlanEntry>()
        if (cursor != null && cursor.moveToFirst()){
            do {
                val rowId = cursor.getLong(0)
                val food = getFoodById(cursor.getInt(1))
                if (food == null){
                    Log.d("DBAdapter", "Failed to fetch Food object from plan entry with id=" + cursor.getInt(0))
                    continue
                } else {
                    food.amount = cursor.getDouble(2)
                    entries.add(
                        PlanEntry(
                            food,
                            rowId
                        )
                    )
                }
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return entries
    }

    fun getPlanEntries(week: Int, year: Int) : List<PlanEntry> {
        val calendar = GregorianCalendar(Locale.GERMANY).apply {
            set(Calendar.YEAR, year)
            set(Calendar.WEEK_OF_YEAR, week)
            firstDayOfWeek = Calendar.MONDAY
        }
        // Retrieve entries after monday of week
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }
        val after = calendar.time

        // Retrieve entries before monday of next week
        calendar.add(Calendar.WEEK_OF_YEAR, 1)
        val before = calendar.time

        // Fetch entries
        return getPlanEntries(after, before)
    }
}
 */