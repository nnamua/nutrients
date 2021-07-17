package com.paulaumann.nutrients.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulaumann.nutrients.data.AppDatabase
import com.paulaumann.nutrients.data.ConsumedFood
import com.paulaumann.nutrients.data.FoodRepository
import com.paulaumann.nutrients.util.toTimeRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    val entries = MutableLiveData<List<ConsumedFood>>()

    var week: Int
    var year: Int

    init {
        // Start at current week
        val calendar = Calendar.getInstance()
        week = calendar.get(Calendar.WEEK_OF_YEAR)
        year = calendar.get(Calendar.YEAR)
        updateEntries()
    }

    fun weekSelected(week: Int, year: Int){
        this.week = week
        this.year = year
        updateEntries()
    }

    private fun updateEntries(){
        val timerange = toTimeRange(week, year)
        viewModelScope.launch {
            foodRepository.getConsumedFood(timerange.first, timerange.second).collect {
                entries.postValue(it)
            }
        }
    }

}