package com.example.collegealertapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class CampusAlertApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val sharedPrefs = getSharedPreferences("CampusAlertPrefs", MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false)

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}