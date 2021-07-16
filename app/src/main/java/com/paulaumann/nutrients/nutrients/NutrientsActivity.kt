package com.paulaumann.nutrients.nutrients
/*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulaumann.nutrients.R
import com.paulaumann.nutrients.adapters.FoodDataListAdapter
import com.paulaumann.nutrients.data.AppDatabase
import com.paulaumann.nutrients.model.Analytics
import com.paulaumann.nutrients.util.WeekPicker
import kotlinx.android.synthetic.main.activity_nutrients.*
import java.util.*

class NutrientsActivity : AppCompatActivity(R.layout.fragment_nutrients){

    private var week: Int
    private var year: Int
    private lateinit var analytics: Analytics
    private lateinit var listAdapter: FoodDataListAdapter

    init {
        // Start at current week
        val calendar = Calendar.getInstance()
        week = calendar.get(Calendar.WEEK_OF_YEAR)
        year = calendar.get(Calendar.YEAR)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize analytics
        analytics = Analytics(AppDatabase.getInstance(this).dao())
        analytics.fetchEntries(week, year)
        listAdapter = FoodDataListAdapter(analytics.sum, this)
        sumFoodData.adapter = listAdapter
        sumFoodData.layoutManager = LinearLayoutManager(this)

        // Bind buttons
        nutrientsBack.setOnClickListener { finish() }
        nutrientsPickWeek.setOnClickListener {
            WeekPicker(::weekSelected).apply {
                show(supportFragmentManager, tag)
            }
        }
    }

    private fun weekSelected(week: Int, year: Int){
        this.week = week
        this.year = year
        update()
    }

    private fun update(){
        analytics.fetchEntries(week, year)
        listAdapter.food = analytics.sum
        listAdapter.notifyDataSetChanged()
    }
}

 */