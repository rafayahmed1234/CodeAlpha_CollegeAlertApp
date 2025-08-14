package com.example.collegealertapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SharedPreferences first
        sharedPreferences = getSharedPreferences("CampusAlertPrefs", MODE_PRIVATE)

        // Check session BEFORE setting the content view
        if (isUserLoggedIn()) {
            // If user is already logged in, go directly to the dashboard
            goToDashboard()
            return // Stop further execution of onCreate
        }

        // If user is NOT logged in, then show the Home screen with Login/Register buttons
        setContentView(R.layout.activity_home)

        val loginBtn = findViewById<Button>(R.id.btnGoToLogin)
        val registerBtn = findViewById<Button>(R.id.btnGoToRegister)

        loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            // finish() is not needed here, user might want to come back
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, `RegisterActivity.kt`::class.java))
            // finish() is not needed here either
        }
    }

    private fun isUserLoggedIn(): Boolean {
        // Check if the 'is_logged_in' flag is true in SharedPreferences
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    private fun goToDashboard() {
        val intent = Intent(this, StudentDashboardActivity::class.java)
        startActivity(intent)
        finish() // Finish HomeActivity so user can't go back to it
    }
}