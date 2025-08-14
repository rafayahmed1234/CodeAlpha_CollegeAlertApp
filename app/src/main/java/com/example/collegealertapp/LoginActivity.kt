package com.example.collegealertapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton // <-- YEH IMPORT ZAROORI HAI
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import java.io.File

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    // ===================================
    // NAYA BACK BUTTON VARIABLE
    // ===================================
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize sharedPreferences here
        sharedPreferences = getSharedPreferences("CampusAlertPrefs", MODE_PRIVATE)

        // Check if user is already logged in
        checkExistingSession()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val registerTextView = findViewById<TextView>(R.id.tvRegister)

        // ==================================================
        // STEP 1: XML se Back Button ko dhoondhein
        // ==================================================
        backButton = findViewById(R.id.backButton)


        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validation
            if (email.isEmpty()) {
                etEmail.error = "Please enter your email"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Please enter a valid email"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Please enter your password"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                etPassword.error = "Password must be at least 6 characters"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            // Dummy login check (Hardcoded credentials)
            if (email == "student@college.com" && password == "abc123") {
                // Save user session data before navigating
                saveUserSession(email)

                val intent = Intent(this, StudentDashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        // Set the OnClickListener on the Register TextView
        registerTextView.setOnClickListener {
            val intent = Intent(this, `RegisterActivity.kt`::class.java)
            startActivity(intent)
        }

        // ==================================================
        // STEP 2: Back Button par Click Listener Lagayein
        // ==================================================
        backButton.setOnClickListener {
            // Isse yeh screen band ho jayegi aur pichli screen par wapas chala jayega
            finish()
        }
    }

    // NEW: Check if user is already logged in
    private fun checkExistingSession() {
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        val userToken = sharedPreferences.getString("user_token", null)

        if (isLoggedIn && !userToken.isNullOrEmpty()) {
            // User is already logged in, redirect to dashboard
            val intent = Intent(this, StudentDashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // NEW: Save user session data
    private fun saveUserSession(email: String) {
        sharedPreferences.edit {
            putBoolean("is_logged_in", true)
            putString("user_token", "dummy_token_${System.currentTimeMillis()}")
            putString("user_email", email)
            putString("user_name", "Student User") // You can get this from API
            putString("user_id", "student_123") // You can get this from API
            putLong("login_time", System.currentTimeMillis())
        }

        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
    }

    private fun clearUserSession() {
        val userSessionKeys = listOf(
            "user_token",
            "user_id",
            "user_email",
            "user_name",
            "is_logged_in",
            "login_time"
        )

        sharedPreferences.edit {
            userSessionKeys.forEach { key ->
                remove(key)
            }
        }

        clearAppCache()

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }

    private fun clearAppCache() {
        try {
            val cacheDir = cacheDir
            if (cacheDir != null && cacheDir.isDirectory) {
                deleteDir(cacheDir)
            }

            val externalCacheDir = externalCacheDir
            if (externalCacheDir != null && externalCacheDir.isDirectory) {
                deleteDir(externalCacheDir)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir == null) return false

        try {
            if (dir.isDirectory) {
                val children = dir.listFiles()
                children?.forEach { child ->
                    deleteDir(child)
                }
            }
            return dir.delete()
        } catch (e: Exception) {
            return false
        }
    }

    private fun clearUserSessionSimple() {
        sharedPreferences.edit {
            clear()
        }
    }

    private fun performLogout() {
        clearUserSession()

        val intent = Intent(this, MainActivity::class.java) // Replace with your login activity
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}