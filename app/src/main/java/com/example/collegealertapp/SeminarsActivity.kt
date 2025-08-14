// SeminarsActivity.kt - UPDATED

package com.example.collegealertapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SeminarsActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var titleText: TextView
    private lateinit var seminarsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seminars)

        initViews()
        setupClickListeners()
        setupRecyclerView()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        titleText = findViewById(R.id.titleText)
        seminarsRecyclerView = findViewById(R.id.seminarsRecyclerView)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }

    private fun setupRecyclerView() {
        // =======================================================
        // YEH HAI AAPKA FIX: Search query haasil karein
        // =======================================================
        val searchQuery = intent.getStringExtra("SEARCH_QUERY")

        val allSeminars = listOf(
            SeminarItem("AI and Machine Learning", "Tech Seminar", "15 March 2025", "10:00 AM", "Hall A"),
            SeminarItem("Digital Marketing", "Business Seminar", "18 March 2025", "2:00 PM", "Hall B"),
            SeminarItem("Web Development", "Tech Workshop", "20 March 2025", "11:00 AM", "Computer Lab"),
            SeminarItem("Entrepreneurship", "Business Talk", "22 March 2025", "3:00 PM", "Auditorium"),
            SeminarItem("Data Science", "Tech Session", "25 March 2025", "9:00 AM", "Hall C")
        )

        // Agar search query hai, toh list ko filter karein
        val filteredSeminars = if (!searchQuery.isNullOrBlank()) {
            titleText.text = "Search Results" // Title badal dein
            allSeminars.filter { seminar ->
                seminar.title.contains(searchQuery, ignoreCase = true)
            }
        } else {
            allSeminars // Agar search nahi hai, toh poori list dikhayein
        }

        val adapter = SeminarsAdapter(filteredSeminars)
        seminarsRecyclerView.layoutManager = LinearLayoutManager(this)
        seminarsRecyclerView.adapter = adapter
    }

    @SuppressLint("GestureBackNavigation")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}

data class SeminarItem(
    val title: String,
    val type: String,
    val date: String,
    val time: String,
    val venue: String
)