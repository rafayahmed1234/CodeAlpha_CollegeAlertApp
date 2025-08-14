// FestsActivity.kt - UPDATED
package com.example.collegealertapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collegealertapp.FestsAdapter
import com.google.firebase.database.*

class FestsActivity : AppCompatActivity() {

    private lateinit var festsRecyclerView: RecyclerView
    private lateinit var festsAdapter: FestsAdapter
    private val festsList = mutableListOf<FestItem>()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fests)

        database = FirebaseDatabase.getInstance().getReference("fests")

        initViews()
        setupRecyclerView()
        fetchFestsFromFirebase()
    }

    private fun initViews() {
        festsRecyclerView = findViewById(R.id.festsRecyclerView)
        findViewById<android.widget.ImageView>(R.id.backButton).setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        festsAdapter = FestsAdapter(festsList)
        festsRecyclerView.layoutManager = LinearLayoutManager(this)
        festsRecyclerView.adapter = festsAdapter
    }

    private fun fetchFestsFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                festsList.clear()
                for (festSnapshot in snapshot.children) {
                    val fest = festSnapshot.getValue(FestItem::class.java)
                    if (fest != null) {
                        festsList.add(fest)
                    }
                }
                festsAdapter.updateData(festsList)
            }

            override fun onCancelled(error: DatabaseError) {
                // ===========================================
                // YEH LINE THEEK KAR DI GAYI HAI
                // ===========================================
                Toast.makeText(this@FestsActivity, "Failed to load fests.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

// Data class yahan ya alag file mein honi chahiye
data class FestItem(
    val title: String? = null,
    val type: String? = null,
    val date: String? = null,
    val time: String? = null,
    val venue: String? = null,
    val fee: String? = null
)