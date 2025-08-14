// NoticesActivity.kt - UPDATED & FIXED

package com.example.collegealertapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collegealertapp.NoticesAdapter
import com.google.firebase.database.*

class NoticesActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var titleText: TextView
    private lateinit var noticesRecyclerView: RecyclerView

    private lateinit var noticesAdapter: NoticesAdapter
    private val noticesList = mutableListOf<NoticeItem>()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notices)

        database = FirebaseDatabase.getInstance().getReference("notices")

        initViews()
        setupClickListeners()
        setupRecyclerView()
        fetchNoticesFromFirebase()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        titleText = findViewById(R.id.titleText)
        noticesRecyclerView = findViewById(R.id.noticesRecyclerView)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        noticesAdapter = NoticesAdapter(noticesList)
        noticesRecyclerView.layoutManager = LinearLayoutManager(this)
        noticesRecyclerView.adapter = noticesAdapter
    }

    // =======================================================
    // YEH FUNCTION POORA THEEK KAR DIYA GAYA HAI
    // =======================================================
    private fun fetchNoticesFromFirebase() {
        val searchQuery = intent.getStringExtra("SEARCH_QUERY")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                noticesList.clear()
                for (noticeSnapshot in snapshot.children) {
                    // FIX #1: Extra parameter hata diya gaya hai
                    val notice = noticeSnapshot.getValue(NoticeItem::class.java)
                    if (notice != null) {
                        noticesList.add(notice)
                    }
                }

                if (!searchQuery.isNullOrBlank()) {
                    titleText.text = "Search Results"

                    // FIX #2 & #3: Filter logic ko saaf kar diya gaya hai
                    val filteredList = noticesList.filter { notice ->
                        val titleMatches = notice.title?.contains(searchQuery, ignoreCase = true) ?: false
                        val descriptionMatches = notice.description?.contains(searchQuery, ignoreCase = true) ?: false
                        titleMatches || descriptionMatches
                    }
                    noticesAdapter.updateData(filteredList)
                } else {
                    noticesAdapter.updateData(noticesList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@NoticesActivity, "Failed to load notices.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

// Data class (yahan ya alag file mein)
data class NoticeItem(
    val title: String? = null,
    val description: String? = null,
    val date: String? = null,
    val priority: String? = null
)