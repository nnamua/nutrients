package com.paulaumann.nutrients.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulaumann.nutrients.data.FoodRepository
import com.paulaumann.nutrients.data.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanNewEntryViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    val food = MutableLiveData<List<Food>>()

    fun findFood(partialName: String){
        viewModelScope.launch {
            foodRepository.getFoodByPartialName(partialName, 35).collect { entries ->
                food.postValue(entries)
            }
        }
    }

}