// NoticesAdapter.kt - UPDATED & FIXED
package com.example.collegealertapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// "val notices" ko "private var notices" se badal dein taaki list update ho sake
class NoticesAdapter(private var notices: List<NoticeItem>) : RecyclerView.Adapter<NoticesAdapter.NoticeViewHolder>() {

    // ViewHolder class should be inside the adapter
    inner class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.noticeTitle)
        val description: TextView = itemView.findViewById(R.id.noticeDescription)
        val date: TextView = itemView.findViewById(R.id.noticeDate)
        val priority: TextView = itemView.findViewById(R.id.noticePriority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notice, parent, false)
        return NoticeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val notice = notices[position]
        holder.title.text = notice.title
        holder.description.text = notice.description
        holder.date.text = notice.date
        holder.priority.text = notice.priority // Priority set properly
    }

    override fun getItemCount(): Int = notices.size

    fun updateData(newNoticesList: List<NoticeItem>) {
        this.notices = newNoticesList
        notifyDataSetChanged()
    }
}