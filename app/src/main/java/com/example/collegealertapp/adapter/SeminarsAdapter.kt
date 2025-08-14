// SeminarsAdapter.kt - UPDATED
package com.example.collegealertapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SeminarsAdapter(private val seminars: List<SeminarItem>) : RecyclerView.Adapter<SeminarsAdapter.SeminarViewHolder>() {

    class SeminarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.seminarTitle)
        val type: TextView = view.findViewById(R.id.seminarType)
        val dateTime: TextView = view.findViewById(R.id.seminarDateTime)
        val venue: TextView = view.findViewById(R.id.seminarVenue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeminarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_seminar, parent, false)
        return SeminarViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeminarViewHolder, position: Int) {
        val seminar = seminars[position]

        holder.title.text = seminar.title
        holder.type.text = seminar.type
        holder.dateTime.text = "${seminar.date} • ${seminar.time}"
        holder.venue.text = "Venue: ${seminar.venue}"
    }

        override fun getItemCount() = seminars.size
}