package com.paulaumann.nutrients.data

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.security.KeyException
import kotlin.math.round

@Entity(tableName = "food")
data class Food(
    @PrimaryKey                                     var id: Int,
    @ColumnInfo(name = "name")                      var name: String,
    @ColumnInfo(name = "category")                  var category: String,
    @ColumnInfo(name = "reference")                 var reference: String,
    @ColumnInfo(name = "energy_kJ")                 var energy_kJ: Int?,
    @ColumnInfo(name = "energy_kcal")               var energy_kcal: Int?,
    @ColumnInfo(name = "fat_total")                 var fat_total: Double?,
    @ColumnInfo(name = "fatty_acids_saturated")     var fatty_acids_saturated: Double?,
    @ColumnInfo(name = "fatty_acids_monounsatured") var fatty_acids_monounsatured: Double?,
    @ColumnInfo(name = "fatty_acids_polyunsatured") var fatty_acids_polyunsatured: Double?,
    @ColumnInfo(name = "cholesterol")               var cholesterol: Double?,
    @ColumnInfo(name = "carbohydrates")             var carbohydrates: Double?,
    @ColumnInfo(name = "sugar")                     var sugar: Double?,
    @ColumnInfo(name = "starch")                    var starch: Double?,
    @ColumnInfo(name = "dietary_fiber")             var dietary_fiber: Double?,
    @ColumnInfo(name = "proteins")                  var proteins: Double?,
    @ColumnInfo(name = "salt")                      var salt: Double?,
    @ColumnInfo(name = "alcohol")                   var alcohol: Double?,
    @ColumnInfo(name = "water")                     var water: Double?,
    @ColumnInfo(name = "vitamin_a_re")              var vitamin_a_re: Int?,
    @ColumnInfo(name = "vitamin_a_rae")             var vitamin_a_rae: Int?,
    @ColumnInfo(name = "retinol")                   var retinol: Int?,
    @ColumnInfo(name = "beta_carotene_bce")         var beta_carotene_bce: Int?,
    @ColumnInfo(name = "beta_carotene")             var beta_carotene: Int?,
    @ColumnInfo(name = "vitamin_b1")                var vitamin_b1: Double?,
    @ColumnInfo(name = "vitamin_b2")                var vitamin_b2: Double?,
    @ColumnInfo(name = "vitamin_b6")                var vitamin_b6: Double?,
    @ColumnInfo(name = "vitamin_b12")               var vitamin_b12: Double?,
    @ColumnInfo(name = "niacin")                    var niacin: Double?,
    @ColumnInfo(name = "folat")                     var folat: Double?,
    @ColumnInfo(name = "pantothenic_acid")          var pantothenic_acid: Double?,
    @ColumnInfo(name = "vitamin_c")                 var vitamin_c: Double?,
    @ColumnInfo(name = "vitamin_d")                 var vitamin_d: Double?,
    @ColumnInfo(name = "vitamin_e_ate")             var vitamin_e_ate: Double?,
    @ColumnInfo(name = "potassium")                 var potassium: Double?,
    @ColumnInfo(name = "sodium")                    var sodium: Double?,
    @ColumnInfo(name = "chloride")                  var chloride: Double?,
    @ColumnInfo(name = "calcium")                   var calcium: Double?,
    @ColumnInfo(name = "magnesium")                 var magnesium: Double?,
    @ColumnInfo(name = "phosphorus")                var phosphorus: Double?,
    @ColumnInfo(name = "iron")                      var iron: Double?,
    @ColumnInfo(name = "iodine")                    var iodine: Double?,
    @ColumnInfo(name = "zinc")                      var zinc: Double?,
    @ColumnInfo(name = "selenium")                  var selenium: Double? ){

    companion object {
        fun empty(): Food {
            return Food(
                -1, "unnamed", "none", "100mg", 0, 0,
                0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0,
                0, 0, 0, 0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
            )
        }

        val keys: Array<String> = arrayOf("id", "name", "category", "reference", "energy_kJ", "energy_kcal",
            "fat_total", "fatty_acids_saturated", "fatty_acids_monounsatured", "fatty_acids_polyunsatured",
            "cholesterol", "carbohydrates", "sugar", "starch", "dietary_fiber", "proteins", "salt", "alcohol",
            "water", "vitamin_a_re", "vitamin_a_rae", "retinol", "beta_carotene_bce", "beta_carotene",
            "vitamin_b1", "vitamin_b2", "vitamin_b6", "vitamin_b12", "niacin", "folat", "pantothenic_acid",
            "vitamin_c", "vitamin_d", "vitamin_e_ate", "potassium", "sodium", "chloride", "calcium",
            "magnesium", "phosphorus", "iron", "iodine", "zinc", "selenium")
    }

    // Only works when reference string is "<amount><unit>"
    fun getReferenceUnit(): String {
        if (!reference.matches(Regex("[0-9]+(kg|L|l|g|ml|mg)"))){
            Log.d("URGENT ERROR", "Reference string does not match regex, returning default 'mg' for '" + reference + "'")
            return "mg"
        }
        var unit = ""
        reference.forEach {
            if (it.isLetter()) unit += it
        }
        return unit
    }

    // Only works when reference string is "<amount><unit>"
    fun getReferenceAmount(): Double {
        if (!reference.matches(Regex("[0-9]+(kg|L|l|g|ml|mg)"))){
            Log.d("URGENT ERROR", "Reference string does not match regex, returning default '100' for '" + reference + "'")
            return 100.0
        }
        var amount = ""
        reference.forEach {
            if (it.isDigit()) amount += it
        }
        return amount.toDouble()
    }

    operator fun get(key: String): Any? {
        return when (key) {
            "id" -> id
            "name" -> name
            "category" -> category
            "reference" -> reference
            "energy_kJ" -> energy_kJ
            "energy_kcal" -> energy_kcal
            "fat_total" -> fat_total
            "fatty_acids_saturated" -> fatty_acids_saturated
            "fatty_acids_monounsatured" -> fatty_acids_monounsatured
            "fatty_acids_polyunsatured" -> fatty_acids_polyunsatured
            "cholesterol" -> cholesterol
            "carbohydrates" -> carbohydrates
            "sugar" -> sugar
            "starch" -> starch
            "dietary_fiber" -> dietary_fiber
            "proteins" -> proteins
            "salt" -> salt
            "alcohol" -> alcohol
            "water" -> water
            "vitamin_a_re" -> vitamin_a_re
            "vitamin_a_rae" -> vitamin_a_rae
            "retinol" -> retinol
            "beta_carotene_bce" -> beta_carotene_bce
            "beta_carotene" -> beta_carotene
            "vitamin_b1" -> vitamin_b1
            "vitamin_b2" -> vitamin_b2
            "vitamin_b6" -> vitamin_b6
            "vitamin_b12" -> vitamin_b12
            "niacin" -> niacin
            "folat" -> folat
            "pantothenic_acid" -> pantothenic_acid
            "vitamin_c" -> vitamin_c
            "vitamin_d" -> vitamin_d
            "vitamin_e_ate" -> vitamin_e_ate
            "potassium" -> potassium
            "sodium" -> sodium
            "chloride" -> chloride
            "calcium" -> calcium
            "magnesium" -> magnesium
            "phosphorus" -> phosphorus
            "iron" -> iron
            "iodine" -> iodine
            "zinc" -> zinc
            "selenium" -> selenium
            else -> null
        }
    }

    operator fun get(index: Int): Any? {
        return when (index) {
            0 -> id
            1 -> name
            2 -> category
            3 -> reference
            4 -> energy_kJ
            5 -> energy_kcal
            6 -> fat_total
            7 -> fatty_acids_saturated
            8 -> fatty_acids_monounsatured
            9 -> fatty_acids_polyunsatured
            10 -> cholesterol
            11 -> carbohydrates
            12 -> sugar
            13 -> starch
            14 -> dietary_fiber
            15 -> proteins
            16 -> salt
            17 -> alcohol
            18 -> water
            19 -> vitamin_a_re
            20 -> vitamin_a_rae
            21 -> retinol
            22 -> beta_carotene_bce
            23 -> beta_carotene
            24 -> vitamin_b1
            25 -> vitamin_b2
            26 -> vitamin_b6
            27 -> vitamin_b12
            28 -> niacin
            29 -> folat
            30 -> pantothenic_acid
            31 -> vitamin_c
            32 -> vitamin_d
            33 -> vitamin_e_ate
            34 -> potassium
            35 -> sodium
            36 -> chloride
            37 -> calcium
            38 -> magnesium
            39 -> phosphorus
            40 -> iron
            41 -> iodine
            42 -> zinc
            43 -> selenium
            else -> null
        }
    }

    operator fun set(key: String, value: Int?){
        when (key) {
            "energy_kJ" -> energy_kJ = value
            "energy_kcal" -> energy_kcal = value
            "fat_total" -> fat_total = value as Double?
            "fatty_acids_saturated" -> fatty_acids_saturated = value as Double?
            "fatty_acids_monounsatured" -> fatty_acids_monounsatured = value as Double?
            "fatty_acids_polyunsatured" -> fatty_acids_polyunsatured = value as Double?
            "cholesterol" -> cholesterol = value as Double?
            "carbohydrates" -> carbohydrates = value as Double?
            "sugar" -> sugar = value as Double?
            "starch" -> starch = value as Double?
            "dietary_fiber" -> dietary_fiber = value as Double?
            "proteins" -> proteins = value as Double?
            "salt" -> salt = value as Double?
            "alcohol" -> alcohol = value as Double?
            "water" -> water = value as Double?
            "vitamin_a_re" -> vitamin_a_re = value
            "vitamin_a_rae" -> vitamin_a_rae = value
            "retinol" -> retinol = value
            "beta_carotene_bce" -> beta_carotene_bce = value
            "beta_carotene" -> beta_carotene = value
            "vitamin_b1" -> vitamin_b1 = value as Double?
            "vitamin_b2" -> vitamin_b2 = value as Double?
            "vitamin_b6" -> vitamin_b6 = value as Double?
            "vitamin_b12" -> vitamin_b12 = value as Double?
            "niacin" -> niacin = value as Double?
            "folat" -> folat = value as Double?
            "pantothenic_acid" -> pantothenic_acid = value as Double?
            "vitamin_c" -> vitamin_c = value as Double?
            "vitamin_d" -> vitamin_d = value as Double?
            "vitamin_e_ate" -> vitamin_e_ate = value as Double?
            "potassium" -> potassium = value as Double?
            "sodium" -> sodium = value as Double?
            "chloride" -> chloride = value as Double?
            "calcium" -> calcium = value as Double?
            "magnesium" -> magnesium = value as Double?
            "phosphorus" -> phosphorus = value as Double?
            "iron" -> iron = value as Double?
            "iodine" -> iodine = value as Double?
            "zinc" -> zinc = value as Double?
            "selenium" -> selenium = value as Double?
            else -> throw KeyException("Unknown key")
        }
    }

    operator fun set(key: String, value: Double?){
        when (key) {
            "energy_kJ" -> energy_kJ = value?.let { round(it).toInt() }
            "energy_kcal" -> energy_kcal = value?.let { round(it).toInt() }
            "fat_total" -> fat_total = value
            "fatty_acids_saturated" -> fatty_acids_saturated = value
            "fatty_acids_monounsatured" -> fatty_acids_monounsatured = value
            "fatty_acids_polyunsatured" -> fatty_acids_polyunsatured = value
            "cholesterol" -> cholesterol = value
            "carbohydrates" -> carbohydrates = value
            "sugar" -> sugar = value
            "starch" -> starch = value
            "dietary_fiber" -> dietary_fiber = value
            "proteins" -> proteins = value
            "salt" -> salt = value
            "alcohol" -> alcohol = value
            "water" -> water = value
            "vitamin_a_re" -> vitamin_a_re = value?.let { round(it).toInt() }
            "vitamin_a_rae" -> vitamin_a_rae = value?.let { round(it).toInt() }
            "retinol" -> retinol = value?.let { round(it).toInt() }
            "beta_carotene_bce" -> beta_carotene_bce = value?.let { round(it).toInt() }
            "beta_carotene" -> beta_carotene = value?.let { round(it).toInt() }
            "vitamin_b1" -> vitamin_b1 = value
            "vitamin_b2" -> vitamin_b2 = value
            "vitamin_b6" -> vitamin_b6 = value
            "vitamin_b12" -> vitamin_b12 = value
            "niacin" -> niacin = value
            "folat" -> folat = value
            "pantothenic_acid" -> pantothenic_acid = value
            "vitamin_c" -> vitamin_c = value
            "vitamin_d" -> vitamin_d = value
            "vitamin_e_ate" -> vitamin_e_ate = value
            "potassium" -> potassium = value
            "sodium" -> sodium = value
            "chloride" -> chloride = value
            "calcium" -> calcium = value
            "magnesium" -> magnesium = value
            "phosphorus" -> phosphorus = value
            "iron" -> iron = value
            "iodine" -> iodine = value
            "zinc" -> zinc = value
            "selenium" -> selenium = value
            else -> throw KeyException("Unknown key")
        }
    }
}