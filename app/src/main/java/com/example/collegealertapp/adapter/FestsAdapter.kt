// FestsAdapter.kt - UPDATED & FIXED
package com.example.collegealertapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FestsAdapter(private var fests: List<FestItem>) : RecyclerView.Adapter<FestsAdapter.FestViewHolder>() {

    class FestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.festTitle)
        val type: TextView = view.findViewById(R.id.festType)
        val fee: TextView = view.findViewById(R.id.festFee)
        val dateTime: TextView = view.findViewById(R.id.festDateTime)
        val venue: TextView = view.findViewById(R.id.festVenue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fest, parent, false)
        return FestViewHolder(view)
    }

    override fun onBindViewHolder(holder: FestViewHolder, position: Int) {
        val fest = fests[position]

        holder.title.text = fest.title
        holder.type.text = fest.type
        holder.fee.text = fest.fee
        holder.dateTime.text = "${fest.date} • ${fest.time}"
        holder.venue.text = "Venue: ${fest.venue}"
    }

    override fun getItemCount() = fests.size

    fun updateData(newFestsList: List<FestItem>) {
        this.fests = newFestsList
        notifyDataSetChanged()
    }
}