package com.example.collegealertapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log // Firebase ke liye import
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging // Firebase ke liye import


class MainActivity : AppCompatActivity() {

    private lateinit var appLogo: ImageView
    private lateinit var appTitle: TextView
    private lateinit var appSubtitle: TextView
    private lateinit var loadingText: TextView
    private lateinit var dot1: View
    private lateinit var dot2: View
    private lateinit var dot3: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        initViews()

        // Start animations
        startSplashAnimations()

        // =======================================================
        // YAHAN TOKEN FETCH KARNE KA CODE ADD KIYA GAYA HAI
        // =======================================================
        fetchFcmToken()

        // =======================================================
        // SAVED NOTIFICATIONS LOAD KARNE KA CODE ADD KIYA GAYA HAI
        // =======================================================
        loadSavedNotifications()

        // Navigate to home after delay
        Handler(Looper.getMainLooper()).postDelayed({
            // Yahan HomeActivity ki jagah LoginActivity par bhejenge
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3500) // 3.5 seconds
    }

    // NAYA FUNCTION
    private fun fetchFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM_TOKEN", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log and toast
            Log.d("FCM_TOKEN", "Fetched Token from MainActivity: $token")
            // Optional: Show a toast for testing
            // Toast.makeText(baseContext, "Token fetched. Check Logcat.", Toast.LENGTH_SHORT).show()
        }
    }

    // =======================================================
    // NAYA FUNCTION - SAVED NOTIFICATIONS LOAD KARNE KE LIYE
    // =======================================================
    private fun loadSavedNotifications() {
        val sharedPref = getSharedPreferences("app_notifications", Context.MODE_PRIVATE)
        val notifications = sharedPref.getStringSet("notifications_list", emptySet())

        Log.d("SAVED_NOTIFICATIONS", "Total saved notifications: ${notifications?.size}")

        notifications?.forEach { notification ->
            val parts = notification.split("|")
            if (parts.size >= 3) {
                val title = parts[0]
                val body = parts[1]
                val timestamp = parts[2].toLong()
                Log.d("SAVED_NOTIFICATIONS", "Loaded: $title - $body")
                // Yahan aap chahe to RecyclerView ya List mein add kar sakte hain
                // Ya phir koi global list mein store kar sakte hain
            }
        }

        // Agar koi notifications hain to toast show kar sakte hain (optional)
        if (!notifications.isNullOrEmpty()) {
            Toast.makeText(this, "Previous notifications loaded: ${notifications.size}", Toast.LENGTH_SHORT).show()
        }
    }

    // =======================================================
    // HELPER FUNCTION - NOTIFICATIONS CLEAR KARNE KE LIYE
    // =======================================================
    private fun clearSavedNotifications() {
        val sharedPref = getSharedPreferences("app_notifications", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove("notifications_list")
        editor.apply()
        Log.d("SAVED_NOTIFICATIONS", "All notifications cleared")
    }

    // =======================================================
    // HELPER FUNCTION - SPECIFIC NOTIFICATION COUNT KARNE KE LIYE
    // =======================================================
    private fun getNotificationCount(): Int {
        val sharedPref = getSharedPreferences("app_notifications", Context.MODE_PRIVATE)
        val notifications = sharedPref.getStringSet("notifications_list", emptySet())
        return notifications?.size ?: 0
    }

    private fun initViews() {
        appLogo = findViewById(R.id.appLogo)
        appTitle = findViewById(R.id.appTitle)
        appSubtitle = findViewById(R.id.appSubtitle)
        loadingText = findViewById(R.id.loadingText)
        dot1 = findViewById(R.id.dot1)
        dot2 = findViewById(R.id.dot2)
        dot3 = findViewById(R.id.dot3)
    }

    // --- Baaki saare animation functions waise hi rahenge ---
    private fun startSplashAnimations() {
        animateLogo()
        animateTitle()
        animateSubtitle()
        animateLoadingDots()
        animateLoadingText()
    }

    private fun animateLogo() {
        // Logo animation code yahan hoga
    }

    private fun animateTitle() {
        // Title animation code yahan hoga
    }

    private fun animateSubtitle() {
        // Subtitle animation code yahan hoga
    }

    private fun animateLoadingDots() {
        // Loading dots animation code yahan hoga
    }

    private fun animateDot(dot: View, delay: Long) {
        // Individual dot animation code yahan hoga
    }

    private fun animateLoadingText() {
        // Loading text animation code yahan hoga
    }

    // navigateToHome function ko bhi update kar diya gaya hai
    // taaki woh LoginActivity par jaaye, na ki HomeActivity par.
}