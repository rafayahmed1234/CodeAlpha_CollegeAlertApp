package com.example.collegealertapp
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.messaging.FirebaseMessaging
class StudentDashboardActivity : AppCompatActivity() {

    // Views from the layout
    private lateinit var notificationIcon: ImageView
    private lateinit var menuIcon: ImageView
    private lateinit var seminarsCard: CardView
    private lateinit var examsCard: CardView
    private lateinit var festsCard: CardView
    private lateinit var noticesCard: CardView
    private lateinit var recruitmentEventCard: CardView
    private lateinit var techEventCard: CardView

    // Bottom Navigation Buttons
    private lateinit var homeButton: LinearLayout
    private lateinit var calendarButton: LinearLayout
    private lateinit var searchButton: LinearLayout
    private lateinit var savedButton: LinearLayout

    // SharedPreferences for user session management
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        sharedPreferences = getSharedPreferences("CampusAlertPrefs", MODE_PRIVATE)

        initViews()
        setupClickListeners()
        checkUserSession()

        // =======================================================
        // YAHAN HUM USER KO TOPICS PAR SUBSCRIBE KARWAYENGE
        // =======================================================
        subscribeToTopics()
    }

    // NAYA FUNCTION: User ko topics par subscribe karwana
    private fun subscribeToTopics() {
        Log.d("FCM_TOPICS", "Subscribing to topics...")

        FirebaseMessaging.getInstance().subscribeToTopic("seminars")
            .addOnCompleteListener { task ->
                val msg =
                    if (task.isSuccessful) "Subscribed to Seminars" else "Failed to subscribe to Seminars"
                Log.d("FCM_TOPICS", msg)
            }

        FirebaseMessaging.getInstance().subscribeToTopic("exams")
            .addOnCompleteListener { task ->
                val msg =
                    if (task.isSuccessful) "Subscribed to Exams" else "Failed to subscribe to Exams"
                Log.d("FCM_TOPICS", msg)
            }

        FirebaseMessaging.getInstance().subscribeToTopic("fests")
            .addOnCompleteListener { task ->
                val msg =
                    if (task.isSuccessful) "Subscribed to Fests" else "Failed to subscribe to Fests"
                Log.d("FCM_TOPICS", msg)
            }

        FirebaseMessaging.getInstance().subscribeToTopic("notices")
            .addOnCompleteListener { task ->
                val msg =
                    if (task.isSuccessful) "Subscribed to Notices" else "Failed to subscribe to Notices"
                Log.d("FCM_TOPICS", msg)
            }
    }

    private fun initViews() {
        notificationIcon = findViewById(R.id.notificationButton)
        menuIcon = findViewById(R.id.menuIcon)
        seminarsCard = findViewById(R.id.seminarsCard)
        examsCard = findViewById(R.id.examsCard)
        festsCard = findViewById(R.id.festsCard)
        noticesCard = findViewById(R.id.noticesCard)
        recruitmentEventCard = findViewById(R.id.recruitment_event_card)
        techEventCard = findViewById(R.id.tech_event_card)
        homeButton = findViewById(R.id.homeButton)
        calendarButton = findViewById(R.id.calendarButton)
        searchButton = findViewById(R.id.searchButton)
        savedButton = findViewById(R.id.savedButton)
    }

    private fun setupClickListeners() {
        notificationIcon.setOnClickListener { openNotificationActivity() }
        menuIcon.setOnClickListener { showPopupMenu(it) }
        seminarsCard.setOnClickListener { openSeminarsActivity() }
        examsCard.setOnClickListener { openExamsActivity() }
        festsCard.setOnClickListener { openFestsActivity() }
        noticesCard.setOnClickListener { openNoticesActivity() }
        recruitmentEventCard.setOnClickListener { openEventDetail("Recruitr 2tile") }
        techEventCard.setOnClickListener { openEventDetail("Tatrucl Stends") }

        homeButton.setOnClickListener {
            Toast.makeText(this, "You are already on the Home screen", Toast.LENGTH_SHORT).show()
        }
        calendarButton.setOnClickListener { openCalendarActivity() }

        searchButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        savedButton.setOnClickListener {
            startActivity(Intent(this, SavedActivity::class.java))
        }
    }

    // Baaki saare functions waise hi rahenge
    private fun openCalendarActivity() {
        try {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Could not open Calendar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openEventDetail(eventName: String) {
        try {
            val intent = Intent(this, EventDetailActivity::class.java)
            when (eventName) {
                "Recruitr 2tile" -> {
                    intent.putExtra("event_name", "Recruitr 2tile")
                    intent.putExtra(
                        "event_description",
                        "Join us for an exciting recruitment event where top companies will be visiting our campus to hire talented students."
                    )
                    intent.putExtra("interested_count", "21,266")
                    intent.putExtra("going_count", "136,79")
                    intent.putExtra("views_count", "94,319")
                    intent.putExtra("event_image_res_id", R.drawable.event_image_1)
                }

                "Tatrucl Stends" -> {
                    intent.putExtra("event_name", "Tatrucl Stends")
                    intent.putExtra(
                        "event_description",
                        "Experience the latest in technology trends and innovations at our tech showcase event."
                    )
                    intent.putExtra("interested_count", "22,319")
                    intent.putExtra("going_count", "411,96")
                    intent.putExtra("views_count", "34,914")
                    intent.putExtra("event_image_res_id", R.drawable.event_image_2)
                }
            }
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening event details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openNotificationActivity() {
        Toast.makeText(this, "Opening Notifications...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, NotificationActivity::class.java)
        startActivity(intent)
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.dashboard_menu, popup.menu)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_profile -> {
                    openProfileActivity()
                    true
                }

                R.id.menu_settings -> {
                    openSettingsActivity()
                    true
                }

                R.id.menu_logout -> {
                    showLogoutDialog()
                    true
                }

                else -> false
            }
        }
        popup.show()
    }

    private fun openProfileActivity() {
        Toast.makeText(this, "Opening Profile...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun openSettingsActivity() {
        Toast.makeText(this, "Opening Settings...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ -> performLogout() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun performLogout() {
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()
        clearUserSession()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    private fun clearUserSession() {
        sharedPreferences.edit().clear().apply()
    }

    private fun checkUserSession() {
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        if (!isLoggedIn) {
            redirectToLogin()
        }
    }

    private fun redirectToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun openSeminarsActivity() {
        Toast.makeText(this, "Opening Seminars...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, SeminarsActivity::class.java)
        startActivity(intent)
    }

    private fun openExamsActivity() {
        Toast.makeText(this, "Opening Exams...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ExamsActivity::class.java)
        startActivity(intent)
    }

    private fun openFestsActivity() {
        Toast.makeText(this, "Opening Fests...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, FestsActivity::class.java)
        startActivity(intent)
    }

    private fun openNoticesActivity() {
        Toast.makeText(this, "Opening Notices...", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, NoticesActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("GestureBackNavigation", "MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Exit App")
            .setMessage("Are you sure you want to exit the app?")
            .setPositiveButton("Yes") { _, _ -> finishAffinity() }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        checkUserSession()
    }
}