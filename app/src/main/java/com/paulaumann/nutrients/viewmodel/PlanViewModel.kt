package com.paulaumann.nutrients.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulaumann.nutrients.adapters.WeekListAdapter
import com.paulaumann.nutrients.data.*
import com.paulaumann.nutrients.data.Food
import com.paulaumann.nutrients.util.toTimeRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * ViewModel for the PlanFragment
 */

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val consumedRepository: ConsumedRepository
) : ViewModel() {

    var selectedDay: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    var week: Int
    var year: Int

    val consumedFood = List(7){ MutableLiveData<List<ConsumedFood>>() }

    init {
        // Start at current week
        val calendar = Calendar.getInstance()
        week = calendar.get(Calendar.WEEK_OF_YEAR)
        year = calendar.get(Calendar.YEAR)

        // Initialize consumed food
        for (i in 0..6) {
            val day = WeekListAdapter.indexToDay(i)
            val timerange = toTimeRange(day, week, year)
            viewModelScope.launch {
                foodRepository.getConsumedFood(timerange.first, timerange.second).collect {
                    consumedFood[i].postValue(it)
                }
            }
        }
    }

    fun updateConsumed(consumed: Consumed){
        viewModelScope.launch {
            consumedRepository.updateConsumed(consumed)
        }
    }

    fun deleteConsumed(consumed: Consumed){
        viewModelScope.launch {
            consumedRepository.deleteConsumed(consumed)
        }
    }

    fun addConsumed(consumed: Consumed){
        viewModelScope.launch {
            consumedRepository.addConsumed(consumed)
        }
    }

    fun addConsumed(food: Food, amount: Double){
        // Construct selected date
        val date = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
            set(Calendar.YEAR, year)
            set(Calendar.WEEK_OF_YEAR, week)
            set(Calendar.DAY_OF_WEEK, selectedDay)
        }.time
        val consumed = Consumed(food.id, date, amount)
        addConsumed(consumed)
    }

    fun weekSelected(week: Int, year: Int){
        this.week = week
        this.year = year

        // Update live data
        for (i in 0..6) {
            val day = WeekListAdapter.indexToDay(i)
            val timerange = toTimeRange(day, week, year)
            viewModelScope.launch {
                foodRepository.getConsumedFood(timerange.first, timerange.second).collect {
                    consumedFood[i].postValue(it)
                }
            }
        }

        // TODO: Have the week indicator textview as live data
    }
}