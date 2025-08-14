package com.example.collegealertapp

import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var calendarView: CalendarView
    private lateinit var eventsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        backButton = findViewById(R.id.backButton)
        calendarView = findViewById(R.id.calendarView)
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView)

        setupListeners()
    }

    private fun setupListeners() {
        backButton.setOnClickListener {
            finish() // Wapas dashboard par jaane ke liye
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "${dayOfMonth}/${month + 1}/${year}"
            Toast.makeText(this, "Selected Date: $selectedDate", Toast.LENGTH_SHORT).show()

            // Yahan aap uss date ke events load kar sakte hain
            loadEventsForDate(selectedDate)
        }
    }

    private fun loadEventsForDate(date: String) {
        // Is function mein aap database ya API se uss date ke events fetch karke
        // RecyclerView mein dikha sakte hain.
        // Abhi ke liye hum ise khali chhod rahe hain.
    }
}

