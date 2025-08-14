package com.example.collegealertapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SavedActivity : AppCompatActivity() {

    private lateinit var savedCountText: TextView
    private lateinit var clearAllButton: ImageView
    private lateinit var emptyStateLayout: LinearLayout

    // Tab chips
    private lateinit var allTab: CardView
    private lateinit var eventsTab: CardView
    private lateinit var seminarsTab: CardView
    private lateinit var noticesTab: CardView

    private var currentTab = "All"
    private var savedItemsCount = 12 // This would come from database/SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        initViews()
        setupClickListeners()
        updateUI()
        setupStatusBar()
    }

    private fun initViews() {
        savedCountText = findViewById(R.id.savedCountText)
        clearAllButton = findViewById(R.id.clearAllButton)
        emptyStateLayout = findViewById(R.id.emptyStateLayout)

        // Tab chips
        allTab = findViewById(R.id.allTab)
        eventsTab = findViewById(R.id.eventsTab)
        seminarsTab = findViewById(R.id.seminarsTab)
        noticesTab = findViewById(R.id.noticesTab)

        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupClickListeners() {
        clearAllButton.setOnClickListener {
            showClearAllDialog()
        }

        // Tab clicks
        allTab.setOnClickListener { selectTab("All", allTab) }
        eventsTab.setOnClickListener { selectTab("Events", eventsTab) }
        seminarsTab.setOnClickListener { selectTab("Seminars", seminarsTab) }
        noticesTab.setOnClickListener { selectTab("Notices", noticesTab) }

        // Setup saved item clicks
        setupSavedItemClicks()
    }

    private fun setupSavedItemClicks() {
        // Add click listeners to saved items
        // Each saved item should have click listener to open detail view
        // and bookmark button to remove from saved items

        // Example: Opening event detail when saved event is clicked
        findViewById<View>(R.id.recruitmentEventCard)?.setOnClickListener {
            openEventDetail("Recruitr 2tile")
        }

        // Add more click listeners for other saved items
    }

    private fun selectTab(tab: String, selectedTab: CardView) {
        currentTab = tab

        // Reset all tabs
        resetAllTabs()

        // Set selected tab to active state
        selectedTab.setCardBackgroundColor(resources.getColor(R.color.white))
        val textView = selectedTab.getChildAt(0) as TextView
        textView.setTextColor(resources.getColor(R.color.primary_purple))

        // Filter content based on selected tab
        filterSavedItems(tab)
    }

    private fun resetAllTabs() {
        val inactiveColor = resources.getColor(R.color.chip_background)
        val inactiveTextColor = resources.getColor(R.color.white)

        listOf(allTab, eventsTab, seminarsTab, noticesTab).forEach { tab ->
            tab.setCardBackgroundColor(inactiveColor)
            val textView = tab.getChildAt(0) as TextView
            textView.setTextColor(inactiveTextColor)
        }
    }

    private fun filterSavedItems(filter: String) {
        // Filter saved items based on selected tab
        // This would typically involve showing/hiding specific card views
        // or updating a RecyclerView adapter

        when (filter) {
            "All" -> showAllItems()
            "Events" -> showEventsOnly()
            "Seminars" -> showSeminarsOnly()
            "Notices" -> showNoticesOnly()
        }
    }

    private fun showAllItems() {
        // Show all saved items
        // Implementation depends on your UI structure
    }

    private fun showEventsOnly() {
        // Show only event items
    }

    private fun showSeminarsOnly() {
        // Show only seminar items
    }

    private fun showNoticesOnly() {
        // Show only notice items
    }

    private fun updateUI() {
        if (savedItemsCount == 0) {
            emptyStateLayout.visibility = View.VISIBLE
            clearAllButton.visibility = View.GONE
            savedCountText.text = "No items saved"
        } else {
            emptyStateLayout.visibility = View.GONE
            clearAllButton.visibility = View.VISIBLE
            savedCountText.text = "$savedItemsCount items saved"
        }
    }

    private fun showClearAllDialog() {
        AlertDialog.Builder(this)
            .setTitle("Clear All Saved Items")
            .setMessage("Are you sure you want to remove all saved items? This action cannot be undone.")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Clear All") { _, _ ->
                clearAllSavedItems()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }

    private fun clearAllSavedItems() {
        // Clear all saved items from database/SharedPreferences
        savedItemsCount = 0
        updateUI()
        Toast.makeText(this, "All saved items cleared", Toast.LENGTH_SHORT).show()

        // Hide all item cards or clear RecyclerView
        hideAllSavedItems()
    }

    private fun hideAllSavedItems() {
        // Hide all saved item cards
        // This would depend on your specific implementation
    }

    private fun removeSavedItem(itemId: String, itemType: String) {
        // Remove specific item from saved items
        savedItemsCount--
        updateUI()
        Toast.makeText(this, "Removed from saved items", Toast.LENGTH_SHORT).show()
    }

    private fun openEventDetail(eventName: String) {
        val intent = Intent(this, EventDetailActivity::class.java)
        intent.putExtra("event_name", eventName)
        startActivity(intent)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun setupStatusBar() {
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

    override fun onResume() {
        super.onResume()
        // Refresh saved items count and UI
        // This is useful when user comes back from detail screen
        // where they might have unsaved an item
        updateUI()
    }
}