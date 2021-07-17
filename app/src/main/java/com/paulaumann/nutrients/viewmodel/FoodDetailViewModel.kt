package com.paulaumann.nutrients.viewmodel

import androidx.lifecycle.*
import com.paulaumann.nutrients.data.FoodRepository
import com.paulaumann.nutrients.data.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    var food: LiveData<Food?> = MutableLiveData()

    fun loadFood(id: Int){
        food = foodRepository.getFoodById(id).asLiveData()
    }
}