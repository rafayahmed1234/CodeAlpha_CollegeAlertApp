package com.example.collegealertapp

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var recentSearchesRecyclerView: RecyclerView
    private lateinit var noRecentSearchesText: TextView
    private lateinit var recentSearchesAdapter: RecentSearchAdapter

    private lateinit var allChip: CardView
    private lateinit var eventsChip: CardView
    private lateinit var seminarsChip: CardView
    private lateinit var examsChip: CardView
    private lateinit var noticesChip: CardView
    private lateinit var allChips: List<CardView>

    private var currentFilter = "All"

    private val recentSearches = mutableListOf(
        "Career Fair" to "Events",
        "Tech Seminar" to "Seminars",
        "Final Exams" to "Exams",
        "AI Workshop" to "Seminars",
        "Library Notice" to "Notices"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews()
        setupRecyclerView()
        setupClickListeners()
        updateRecentSearchesUI()
    }

    /** Initialize UI elements **/
    private fun initViews() {
        searchEditText = findViewById(R.id.searchEditText)
        recentSearchesRecyclerView = findViewById(R.id.recentSearchesRecyclerView)
        noRecentSearchesText = findViewById(R.id.noRecentSearchesText)

        allChip = findViewById(R.id.allChip)
        eventsChip = findViewById(R.id.eventsChip)
        seminarsChip = findViewById(R.id.seminarsChip)
        examsChip = findViewById(R.id.examsChip)
        noticesChip = findViewById(R.id.noticesChip)
        allChips = listOf(allChip, eventsChip, seminarsChip, examsChip, noticesChip)

        // Back button
        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    /** Setup RecyclerView **/
    private fun setupRecyclerView() {
        recentSearchesAdapter = RecentSearchAdapter(
            searches = emptyList(),
            onItemClick = { query ->
                searchEditText.setText(query)
                navigateToSearchResults(query)
            },
            onRemoveClick = { query ->
                recentSearches.removeAll { it.first.equals(query, ignoreCase = true) }
                updateRecentSearchesUI()
            }
        )
        recentSearchesRecyclerView.layoutManager = LinearLayoutManager(this)
        recentSearchesRecyclerView.adapter = recentSearchesAdapter
    }

    /** Setup Click Listeners **/
    private fun setupClickListeners() {
        searchEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                val query = searchEditText.text.toString().trim()
                if (query.isNotEmpty()) {
                    navigateToSearchResults(query)
                }
                true
            } else {
                false
            }
        }

        allChip.setOnClickListener { selectFilter("All", it as CardView) }
        eventsChip.setOnClickListener { selectFilter("Events", it as CardView) }
        seminarsChip.setOnClickListener { selectFilter("Seminars", it as CardView) }
        examsChip.setOnClickListener { selectFilter("Exams", it as CardView) }
        noticesChip.setOnClickListener { selectFilter("Notices", it as CardView) }
    }

    /** Navigate to relevant activity **/
    private fun navigateToSearchResults(query: String) {
        val targetActivityClass: Class<*> = when (currentFilter) {
            "Events" -> FestsActivity::class.java
            "Seminars" -> SeminarsActivity::class.java
            "Exams" -> ExamsActivity::class.java
            "Notices" -> NoticesActivity::class.java
            else -> SearchResultsActivity::class.java
        }

        addToRecentSearches(query, currentFilter.takeIf { it != "All" } ?: "General")

        val intent = Intent(this, targetActivityClass).apply {
            putExtra("SEARCH_QUERY", query)
        }
        startActivity(intent)
    }

    /** Filter selection **/
    private fun selectFilter(filter: String, selectedChip: CardView) {
        currentFilter = filter
        updateChipSelection(selectedChip)
        updateRecentSearchesUI()
    }

    /** Update chip UI **/
    private fun updateChipSelection(selectedChip: CardView) {
        allChips.forEach { chip ->
            chip.setCardBackgroundColor(ContextCompat.getColor(this, R.color.chip_background))
            (chip.getChildAt(0) as TextView)
                .setTextColor(ContextCompat.getColor(this, R.color.white))
        }
        selectedChip.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        (selectedChip.getChildAt(0) as TextView)
            .setTextColor(ContextCompat.getColor(this, R.color.primary_purple))
    }

    /** Update recent searches list UI **/
    private fun updateRecentSearchesUI() {
        val filteredSearches = if (currentFilter == "All") {
            recentSearches
        } else {
            recentSearches.filter { it.second.equals(currentFilter, ignoreCase = true) }
        }

        if (filteredSearches.isEmpty()) {
            recentSearchesRecyclerView.visibility = View.GONE
            noRecentSearchesText.visibility = View.VISIBLE
        } else {
            recentSearchesRecyclerView.visibility = View.VISIBLE
            noRecentSearchesText.visibility = View.GONE
            recentSearchesAdapter.updateData(filteredSearches)
        }
    }

    /** Add to recent searches **/
    private fun addToRecentSearches(query: String, category: String) {
        recentSearches.removeAll { it.first.equals(query, ignoreCase = true) }
        recentSearches.add(0, query to category)
        // TODO: Save in SharedPreferences
        updateRecentSearchesUI()
    }
}
