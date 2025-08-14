package com.example.collegealertapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.collegealertapp.R
import com.example.collegealertapp.model.Event

class EventAdapter(private val eventList: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    // This class holds the views for each item in the list.
    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tvEventTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tvEventDescription)
        val dateTextView: TextView = itemView.findViewById(R.id.tvEventDate)
    }

    // This method creates a new ViewHolder by inflating the item_event.xml layout.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    // This method returns the total number of items in the list.
    override fun getItemCount(): Int {
        return eventList.size
    }

    // This method binds the data from the eventList to the views in the ViewHolder.
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.titleTextView.text = event.title
        holder.descriptionTextView.text = event.description
        holder.dateTextView.text = event.date
    }
}