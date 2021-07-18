package com.paulaumann.nutrients

import androidx.fragment.app.Fragment
import com.paulaumann.nutrients.data.AppDatabase

/**
 * A BaseFragment for each page in this application.
 * Provides basic fields for easier access.
 */

abstract class BaseFragment : Fragment(){

    protected val mainActivity: MainActivity
        get() = activity as MainActivity

    protected val appDatabase: AppDatabase
        get() = AppDatabase.getInstance(requireActivity())

    protected val navController by lazy {
        (activity as MainActivity).navController
    }

}