package com.example.collegealertapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class EventDetailActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var headerTitle: TextView
    private lateinit var eventImage: ImageView
    private lateinit var eventStatus: TextView
    private lateinit var eventTitle: TextView
    private lateinit var eventCategory: TextView
    private lateinit var eventDateTime: TextView
    private lateinit var eventLocation: TextView
    private lateinit var eventOrganizer: TextView
    private lateinit var eventDescription: TextView
    private lateinit var interestedCount: TextView
    private lateinit var goingCount: TextView
    private lateinit var viewsCount: TextView
    private lateinit var interestedButton: CardView
    private lateinit var goingButton: CardView

    // Track user interactions
    private var isUserInterested = false
    private var isUserGoing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        initViews()
        setupClickListeners()
        loadEventData()
        setupStatusBar()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        headerTitle = findViewById(R.id.headerTitle)
        eventImage = findViewById(R.id.eventImage)
        eventStatus = findViewById(R.id.eventStatus)
        eventTitle = findViewById(R.id.eventTitle)
        eventCategory = findViewById(R.id.eventCategory)
        eventDateTime = findViewById(R.id.eventDateTime)
        eventLocation = findViewById(R.id.eventLocation)
        eventOrganizer = findViewById(R.id.eventOrganizer)
        eventDescription = findViewById(R.id.eventDescription)
        interestedCount = findViewById(R.id.interestedCount)
        goingCount = findViewById(R.id.goingCount)
        viewsCount = findViewById(R.id.viewsCount)
        interestedButton = findViewById(R.id.interestedButton)
        goingButton = findViewById(R.id.goingButton)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            onBackPressed()
        }

        interestedButton.setOnClickListener {
            toggleInterested()
        }

        goingButton.setOnClickListener {
            toggleGoing()
        }
    }

    private fun loadEventData() {
        // Get event data from intent
        val eventName = intent.getStringExtra("event_name") ?: "Event Name"
        val eventDesc = intent.getStringExtra("event_description") ?: "Event description not available"
        val interestedCountValue = intent.getStringExtra("interested_count") ?: "0"
        val goingCountValue = intent.getStringExtra("going_count") ?: "0"
        val viewsCountValue = intent.getStringExtra("views_count") ?: "0"

        val imageResId = intent.getIntExtra("event_image_res_id", 0)

        // Set data to views
        eventTitle.text = eventName
        eventDescription.text = eventDesc
        interestedCount.text = interestedCountValue
        goingCount.text = goingCountValue
        viewsCount.text = viewsCountValue

        // Set default values for demonstration
        if (imageResId != 0) {
            eventImage.setImageResource(imageResId)
        }
        if (eventName == "Recruitr 2tile") {
            eventCategory.text = "Career Development"
            eventDateTime.text = "March 25, 2024 • 10:00 AM"
            eventLocation.text = "Main Auditorium, Block A"
            eventOrganizer.text = "Career Development Cell"
        } else if (eventName == "Tatrucl Stends") {
            eventCategory.text = "Technology"
            eventDateTime.text = "March 28, 2024 • 2:00 PM"
            eventLocation.text = "Tech Hub, Block C"
            eventOrganizer.text = "Computer Science Department"
        }

    }


    private fun toggleInterested() {
        isUserInterested = !isUserInterested

        if (isUserInterested) {
            Toast.makeText(this, "Added to interested events!", Toast.LENGTH_SHORT).show()
            // Update UI to show interested state
            updateInterestedCount(1)
        } else {
            Toast.makeText(this, "Removed from interested events", Toast.LENGTH_SHORT).show()
            // Update UI to show not interested state
            updateInterestedCount(-1)
        }

        // Here you would typically make an API call to update the server
        updateButtonStates()
    }

    private fun toggleGoing() {
        isUserGoing = !isUserGoing

        if (isUserGoing) {
            Toast.makeText(this, "Great! You're going to this event!", Toast.LENGTH_SHORT).show()
            // Automatically mark as interested if going
            if (!isUserInterested) {
                isUserInterested = true
                updateInterestedCount(1)
            }
            updateGoingCount(1)
        } else {
            Toast.makeText(this, "Removed from going events", Toast.LENGTH_SHORT).show()
            updateGoingCount(-1)
        }

        // Here you would typically make an API call to update the server
        updateButtonStates()
    }

    private fun updateInterestedCount(delta: Int) {
        val currentCount = interestedCount.text.toString().replace(",", "").toIntOrNull() ?: 0
        val newCount = (currentCount + delta).coerceAtLeast(0)
        interestedCount.text = formatCount(newCount)
    }

    private fun updateGoingCount(delta: Int) {
        val currentCount = goingCount.text.toString().replace(",", "").toIntOrNull() ?: 0
        val newCount = (currentCount + delta).coerceAtLeast(0)
        goingCount.text = formatCount(newCount)
    }

    private fun formatCount(count: Int): String {
        return when {
            count >= 1000000 -> "${count / 1000000}.${(count % 1000000) / 100000}M"
            count >= 1000 -> "${count / 1000},${String.format("%03d", count % 1000)}"
            else -> count.toString()
        }
    }

    private fun updateButtonStates() {
        // You can update button appearance based on user state
        // For example, change colors, icons, or text
    }

    private fun setupStatusBar() {
        // Make status bar transparent
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.decorView.systemUiVisibility =
            android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    @SuppressLint("GestureBackNavigation")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}

