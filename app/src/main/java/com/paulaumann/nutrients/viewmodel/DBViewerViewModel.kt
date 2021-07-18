package com.paulaumann.nutrients.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulaumann.nutrients.data.ConsumedRepository
import com.paulaumann.nutrients.data.FoodRepository
import com.paulaumann.nutrients.data.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the DBViewerFragment
 */

@HiltViewModel
class DBViewerViewModel @Inject internal constructor(
    private val foodRepository: FoodRepository,
    private val consumedRepository: ConsumedRepository
) : ViewModel() {

    // TODO: Put shared methods with [PlanNewEntryViewModel] in base class (maybe)

    val entries = MutableLiveData<List<Food>>()

    fun findFood(partialName: String){
        viewModelScope.launch {
            foodRepository.getFoodByPartialName(partialName, 35).collect { dbEntries ->
                entries.postValue(dbEntries)
            }
        }
    }

}