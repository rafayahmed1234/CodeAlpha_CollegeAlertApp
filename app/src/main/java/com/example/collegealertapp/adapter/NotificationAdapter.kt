package com.example.collegealertapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter(
    private val notifications: List<NotificationItem>,
    private val onItemClick: (NotificationItem) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification_modern, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount(): Int = notifications.size

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.cardViewNotification)
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val messageTextView: TextView = itemView.findViewById(R.id.textViewMessage)
        private val timeTextView: TextView = itemView.findViewById(R.id.textViewTime)
        private val priorityTextView: TextView = itemView.findViewById(R.id.textViewPriority)
        private val typeIconImageView: ImageView = itemView.findViewById(R.id.imageViewTypeIcon)
        private val actionIconImageView: ImageView = itemView.findViewById(R.id.imageViewActionIcon)
        private val unreadDot: View = itemView.findViewById(R.id.viewUnreadDot)

        fun bind(notification: NotificationItem) {
            titleTextView.text = notification.title
            timeTextView.text = getTimeAgo(notification.timestamp)
            priorityTextView.text = notification.priority

            // Set type icon and colors based on notification type
            setupTypeIcon(notification.type)

            // Set priority styling
            setupPriorityStyle(notification.priority)

            // Set read/unread state
            setupReadState(notification.isRead)

            // Set click listener
            itemView.setOnClickListener {
                onItemClick(notification)
            }
        }

        private fun setupTypeIcon(type: String) {
            val (iconRes, iconColor, bgColor) = when (type.lowercase()) {
                "seminar" -> Triple(R.drawable.ic_seminar, Color.WHITE, ContextCompat.getColor(itemView.context, R.color.seminar_color))
                "exam" -> Triple(R.drawable.ic_exam, Color.WHITE, ContextCompat.getColor(itemView.context, R.color.exam_color))
                "fest" -> Triple(R.drawable.ic_fest, Color.WHITE, ContextCompat.getColor(itemView.context, R.color.fest_color))
                "notice" -> Triple(R.drawable.ic_notice, Color.WHITE, ContextCompat.getColor(itemView.context, R.color.notice_color))
                else -> Triple(R.drawable.ic_notification, Color.WHITE, ContextCompat.getColor(itemView.context, R.color.default_color))
            }

            typeIconImageView.setImageResource(iconRes)
            typeIconImageView.setColorFilter(iconColor)
            typeIconImageView.setBackgroundColor(bgColor)
        }

        private fun setupPriorityStyle(priority: String) {
            val (priorityColor, textColor) = when (priority.lowercase()) {
                "high" -> Pair(ContextCompat.getColor(itemView.context, R.color.priority_high), Color.WHITE)
                "low" -> Pair(ContextCompat.getColor(itemView.context, R.color.priority_low), Color.WHITE)
                else -> Pair(ContextCompat.getColor(itemView.context, R.color.priority_normal), Color.WHITE)
            }

            priorityTextView.setBackgroundColor(priorityColor)
            priorityTextView.setTextColor(textColor)
        }

        private fun setupReadState(isRead: Boolean) {
            if (isRead) {
                // Read state - muted colors
                cardView.alpha = 0.7f
                unreadDot.visibility = View.GONE
                titleTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_secondary))
                messageTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_tertiary))
            } else {
                // Unread state - bright colors
                cardView.alpha = 1.0f
                unreadDot.visibility = View.VISIBLE
                titleTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_primary))
                messageTextView.setTextColor(ContextCompat.getColor(itemView.context, R.color.text_secondary))
            }
        }

        private fun getTimeAgo(timestamp: Long): String {
            val now = System.currentTimeMillis()
            val diff = now - timestamp

            return when {
                diff < 60000 -> "Just now"
                diff < 3600000 -> "${diff / 60000}m ago"
                diff < 86400000 -> "${diff / 3600000}h ago"
                diff < 604800000 -> "${diff / 86400000}d ago"
                else -> {
                    val sdf = java.text.SimpleDateFormat("MMM dd", java.util.Locale.getDefault())
                    sdf.format(java.util.Date(timestamp))
                }
            }
        }
    }
}