package com.paulaumann.nutrients.viewmodel

import androidx.lifecycle.*
import com.paulaumann.nutrients.data.AppDatabase
import com.paulaumann.nutrients.data.FoodRepository
import com.paulaumann.nutrients.model.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    foodRepository: FoodRepository
) : ViewModel() {

    val food: LiveData<Food?>

    init {
        val foodId: Int = savedStateHandle.get<Int>(FOOD_ID_KEY)!!
        food = foodRepository.getFoodById(foodId).asLiveData()
    }

    companion object {
        const val FOOD_ID_KEY = "foodId"
    }
}