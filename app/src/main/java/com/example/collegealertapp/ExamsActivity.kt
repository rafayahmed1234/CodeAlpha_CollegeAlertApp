// ExamsActivity.kt - FINAL & FIXED VERSION
package com.example.collegealertapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collegealertapp.ExamsAdapter
import com.google.firebase.database.*

class ExamsActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var titleText: TextView
    private lateinit var examsRecyclerView: RecyclerView

    private lateinit var examsAdapter: ExamsAdapter
    private val examsList = mutableListOf<ExamItem>()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exams)

        database = FirebaseDatabase.getInstance().getReference("exams")

        initViews()
        setupClickListeners()
        setupRecyclerView()
        fetchExamsFromFirebase()
    }

    private fun initViews() {
        backButton = findViewById(R.id.backButton)
        titleText = findViewById(R.id.titleText)
        examsRecyclerView = findViewById(R.id.examsRecyclerView)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        examsAdapter = ExamsAdapter(examsList) // Adapter sirf ek baar banega
        examsRecyclerView.layoutManager = LinearLayoutManager(this)
        examsRecyclerView.adapter = examsAdapter
    }

    private fun fetchExamsFromFirebase() {
        val searchQuery = intent.getStringExtra("SEARCH_QUERY")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                examsList.clear()
                for (examSnapshot in snapshot.children) {
                    val exam = examSnapshot.getValue(ExamItem::class.java)
                    if (exam != null) {
                        examsList.add(exam)
                    }
                }

                // =======================================================
                // YEH HAI AAPKA MAIN FIX: Naya adapter banane ki jagah data update karein
                // =======================================================
                if (!searchQuery.isNullOrBlank()) {
                    titleText.text = "Search Results"
                    val filteredList = examsList.filter {
                        it.subject?.contains(searchQuery, ignoreCase = true) == true
                    }
                    examsAdapter.updateData(filteredList) // Data update karein
                } else {
                    examsAdapter.updateData(examsList) // Data update karein
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ExamsActivity, "Failed to load exams: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

// Data class yahan neeche hi rakhein ya alag file mein
data class ExamItem(
    val subject: String? = null,
    val type: String? = null,
    val date: String? = null,
    val time: String? = null,
    val venue: String? = null,
    val duration: String? = null
)