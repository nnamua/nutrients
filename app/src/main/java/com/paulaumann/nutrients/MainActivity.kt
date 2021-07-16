package com.paulaumann.nutrients

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.paulaumann.nutrients.data.AppDatabase
import com.paulaumann.nutrients.viewmodel.DBViewerViewModel
import com.paulaumann.nutrients.viewmodel.PlanViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val planViewModel: PlanViewModel by viewModels()
        val dbViewModel: DBViewerViewModel by viewModels()

        /*
        // Set status bar to white with black icons
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        window.statusBarColor = ContextCompat.getColor(this, R.color.lightGrey)

        // Load database from assets if necessary
        val db = AppDatabase.getInstance(this)

        // Assign onClickListeners



        */
    }
}