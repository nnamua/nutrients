package com.paulaumann.nutrients

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * I only added this because Hilt required it.
 */
@HiltAndroidApp
class MainApplication : Application()