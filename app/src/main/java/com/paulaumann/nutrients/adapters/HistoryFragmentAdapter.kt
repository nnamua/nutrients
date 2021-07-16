package com.paulaumann.nutrients.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paulaumann.nutrients.history.StackBarFragment
import com.paulaumann.nutrients.model.Food
import java.util.*

class HistoryFragmentAdapter(private val itemcount: Int, fm: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fm, lifecycle) {

    var weeklyFoodList: List<Food>
    private var week: Int
    private var year: Int

    init {
        // Start at current week
        val calendar = Calendar.getInstance()
        week = calendar.get(Calendar.WEEK_OF_YEAR)
        year = calendar.get(Calendar.YEAR)
        // Initialize weekly food list
        weeklyFoodList = listOf()
    }

    override fun getItemCount(): Int = itemcount

    override fun createFragment(position: Int): Fragment {
        return StackBarFragment(position, weeklyFoodList)
    }

    fun update(week: Int, year: Int){
        this.week = week
        this.year = year
        weeklyFoodList = listOf()
    }
}