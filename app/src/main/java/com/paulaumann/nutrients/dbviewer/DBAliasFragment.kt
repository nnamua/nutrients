package com.paulaumann.nutrients.dbviewer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.paulaumann.nutrients.R

/**
 * This Fragment specifically allows the user to add aliases to the database
 */
class DBAliasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dbviewer_alias, container, false)
    }

}
