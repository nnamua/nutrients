package com.paulaumann.nutrients.util
/*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.adapters.FoodDataListAdapter
import com.paulaumann.nutrients.data.AppDatabase
import com.paulaumann.nutrients.model.Food
import kotlinx.android.synthetic.main.activity_food.*

class FoodActivity : AppCompatActivity(R.layout.fragment_food_detail) {

    private lateinit var food: Food

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set status bar to white with black icons
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.statusBarColor = ContextCompat.getColor(this, R.color.lightGrey)

        // Get food object from database (by retrieving id from intent)
        val foodId = intent.getIntExtra("foodId", -1)
        if (foodId == -1){
            Toast.makeText(this, "No food id was found", Toast.LENGTH_SHORT).show()
            Log.d("FoodActivity", "Could not find foodId extra.")
            finish()
        }
        val dbFood = AppDatabase.getInstance(this).dao().getFoodById(foodId)
        if (dbFood == null){
            Toast.makeText(this, "Food was not found in database", Toast.LENGTH_SHORT).show()
            Log.d("FoodActivity", "Could not find food id in database.")
            finish()
        } else {
            food = dbFood
        }

        // food is initialized here
        foodName.text = food.name
        foodCategory.text = food.category.replace(';', '\n')

        // Initialize list
        val adapter = FoodDataListAdapter(food, this)
        foodDataList.adapter = adapter
        foodDataList.layoutManager = LinearLayoutManager(this)

        // Bind back button
        foodBackButton.setOnClickListener {
            finish()
        }

    }
}
*/