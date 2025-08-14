// RecentSearchAdapter.kt
package com.example.collegealertapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecentSearchAdapter(
    private var searches: List<Pair<String, String>>,
    private val onItemClick: (String) -> Unit,
    private val onRemoveClick: (String) -> Unit
) : RecyclerView.Adapter<RecentSearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.recentSearchText)
        val removeButton: ImageView = view.findViewById(R.id.removeRecentSearchButton)
        val rootLayout: View = view.findViewById(R.id.recentSearchItemRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_search, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchQuery = searches[position].first
        holder.textView.text = searchQuery

        holder.rootLayout.setOnClickListener {
            onItemClick(searchQuery)
        }
        holder.removeButton.setOnClickListener {
            onRemoveClick(searchQuery)
        }
    }

    override fun getItemCount() = searches.size

    fun updateData(newSearches: List<Pair<String, String>>) {
        this.searches = newSearches
        notifyDataSetChanged()
    }
}

