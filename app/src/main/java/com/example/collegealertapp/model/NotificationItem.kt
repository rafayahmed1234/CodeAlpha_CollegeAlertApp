// NotificationItem.kt
package com.example.collegealertapp

data class NotificationItem(
    val id: String,
    val title: String,
    val body: String,
    val type: String,
    val timestamp: Long,
    val eventDate: String,
    val location: String,
    val priority: String,
    val isRead: Boolean
)

