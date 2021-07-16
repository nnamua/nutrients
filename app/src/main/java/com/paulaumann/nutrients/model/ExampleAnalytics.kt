package com.paulaumann.nutrients.model

/*
import android.util.Log
import com.paulaumann.nutrients.db.FoodDAO
import java.util.*

class ExampleAnalytics(dao: FoodDAO)
    : Analytics(dao) {

    public override fun fetchEntries(week: Int, year: Int) {
        // Create example entries
        val examples = arrayOf(
            // Monday
            arrayOf<Number>(10446, 500.0), // Brot
            arrayOf<Number>(703, 50.0),    // Schokoaufstrich
            arrayOf<Number>(567, 50.0),    // Frischkäse
            arrayOf<Number>(13388, 120.0), // Bohnen
            arrayOf<Number>(1018, 200.0),  // Paprika
            arrayOf<Number>(632, 125.0),   // Mais
            // Tuesday
            arrayOf<Number>(10446, 500.0),
            arrayOf<Number>(703, 50.0),
            arrayOf<Number>(567, 50.0),
            arrayOf<Number>(381, 150.0),   // Banane
            arrayOf<Number>(14024, 700.0), // Cheeseburger
            // Wednesday
            arrayOf<Number>(10446, 500.0),
            arrayOf<Number>(703, 50.0),
            arrayOf<Number>(567, 50.0),
            arrayOf<Number>(376, 150.0),    // Apfel
            arrayOf<Number>(1572, 800.0),   // Döner
            // Thursday
            arrayOf<Number>(10446, 500.0),
            arrayOf<Number>(703, 50.0),
            arrayOf<Number>(567, 50.0),
            arrayOf<Number>(376, 150.0),   // Apfel
            arrayOf<Number>(1534, 500.0),  // Pizza Hawaii
            // Friday
            arrayOf<Number>(10446, 500.0),
            arrayOf<Number>(703, 50.0),
            arrayOf<Number>(567, 50.0),
            arrayOf<Number>(381, 150.0),   // Banane
            arrayOf<Number>(1514, 750.0),  // Milchreis
            arrayOf<Number>(644, 100.0),   // Kirschen
            // Saturday
            arrayOf<Number>(10446, 500.0),
            arrayOf<Number>(703, 50.0),
            arrayOf<Number>(567, 50.0),
            arrayOf<Number>(376, 150.0),   // Apfel
            arrayOf<Number>(1018, 500.0),  // Paprika
            arrayOf<Number>(549, 300.0),   // Schafskäse
            arrayOf<Number>(1003, 200.0),  // Aubergine
            // Sunday
            arrayOf<Number>(10446, 500.0),
            arrayOf<Number>(703, 50.0),
            arrayOf<Number>(567, 50.0),
            arrayOf<Number>(381, 150.0),   // Banane
            arrayOf<Number>(13276, 300.0), // Hühnchen
            arrayOf<Number>(1057, 100.0),  // Kartoffeln
            arrayOf<Number>(1004, 120.0)   // Blumenkohl
        )

        val exampleEntries = mutableListOf<PlanEntry>()
        for (example in examples){
            val id = example[0].toInt()
            val food = dao.getFoodById(id)
            if (food == null) {
                Log.d("ExampleAnalytics", "Could not fetch food for id $id.")
                continue
            }
            //val entry = PlanEntry(food, EntryInfo(Date(0), example[1].toDouble(), food.id))
            exampleEntries.add(entry)
        }

        entries = exampleEntries
    }
}

 */