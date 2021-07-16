package com.paulaumann.nutrients

import androidx.fragment.app.Fragment
import com.paulaumann.nutrients.data.AppDatabase

abstract class BaseFragment : Fragment(){

    protected val mainActivity: MainActivity
        get() = activity as MainActivity

    protected val appDatabase: AppDatabase
        get() = AppDatabase.getInstance(requireActivity())

}