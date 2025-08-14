package com.example.collegealertapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class NotificationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var toolbar: MaterialToolbar
    private val notificationsList = mutableListOf<NotificationItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        initViews()
        setupToolbar()
        setupRecyclerView()
        loadNotifications()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerViewNotifications)
        toolbar = findViewById(R.id.toolbar)

        findViewById<ImageView>(R.id.imageViewBack).setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Notifications"
        }
    }

    private fun setupRecyclerView() {
        notificationAdapter = NotificationAdapter(notificationsList) { notification ->
            handleNotificationClick(notification)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = notificationAdapter
        }
    }

    private fun loadNotifications() {
        notificationsList.clear()
        notificationsList.addAll(getSampleNotifications())
        notificationAdapter.notifyDataSetChanged()
        updateEmptyState()
    }

    private fun getSampleNotifications(): List<NotificationItem> {
        return listOf(
            NotificationItem(
                id = "1",
                title = "Exam schedule updated",
                body = "Mid-term examination schedule has been updated. Check the latest timetable for Computer Science Department.",
                type = "exam",
                timestamp = System.currentTimeMillis() - 7200000,
                eventDate = "Next Week",
                location = "Main Building",
                priority = "high",
                isRead = false
            ),
            NotificationItem(
                id = "2",
                title = "Machine Learning Seminar",
                body = "Join us for an exciting seminar on Machine Learning and AI applications in modern technology.",
                type = "seminar",
                timestamp = System.currentTimeMillis() - 7200000,
                eventDate = "Tomorrow, 2:00 PM",
                location = "Auditorium A",
                priority = "high",
                isRead = false
            ),
            NotificationItem(
                id = "3",
                title = "Tech Fest Registration Open",
                body = "Registration for annual technical fest is now open. Participate in coding competitions, robotics, and more.",
                type = "fest",
                timestamp = System.currentTimeMillis() - 32400000,
                eventDate = "Dec 15-17, 2024",
                location = "Campus Ground",
                priority = "normal",
                isRead = true
            ),
            NotificationItem(
                id = "4",
                title = "Library Notice",
                body = "Central library will remain closed on Sunday for maintenance work and system upgrade.",
                type = "notice",
                timestamp = System.currentTimeMillis() - 32400000,
                eventDate = "This Sunday",
                location = "Central Library",
                priority = "normal",
                isRead = true
            ),
            NotificationItem(
                id = "5",
                title = "Cultural Fest Auditions",
                body = "Auditions for dance, music, and drama competitions are starting next week. Register now!",
                type = "fest",
                timestamp = System.currentTimeMillis() - 86400000,
                eventDate = "Next Monday",
                location = "Cultural Hall",
                priority = "normal",
                isRead = true
            )
        )
    }

    private fun handleNotificationClick(notification: NotificationItem) {
        markAsRead(notification)
        when (notification.type) {
            "seminar" -> { /* Navigate to seminar activity */ }
            "exam" -> { /* Navigate to exam activity */ }
            "fest" -> { /* Navigate to fest activity */ }
            "notice" -> { /* Navigate to notice activity */ }
        }
    }

    private fun markAsRead(notification: NotificationItem) {
        val index = notificationsList.indexOfFirst { it.id == notification.id }
        if (index != -1) {
            Log.d("NotificationClick", "Notification at index $index clicked")
            // Optionally update isRead
            // notificationsList[index] = notificationsList[index].copy(isRead = true)
            // notificationAdapter.notifyItemChanged(index)
        }
    }

    private fun updateEmptyState() {
        val emptyStateView = findViewById<View>(R.id.emptyStateView)
        if (notificationsList.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyStateView?.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyStateView?.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { onBackPressed(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
