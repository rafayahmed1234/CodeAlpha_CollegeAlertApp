package com.example.collegealertapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupQuickActions(view)
        updateNotificationCount(view)
    }

    private fun setupQuickActions(view: View) {
        view.findViewById<CardView>(R.id.cardSeminars).setOnClickListener {
            startActivity(Intent(requireContext(), SeminarsActivity::class.java))
        }

        view.findViewById<CardView>(R.id.cardExams).setOnClickListener {
            startActivity(Intent(requireContext(), ExamsActivity::class.java))
        }

        view.findViewById<CardView>(R.id.cardFests).setOnClickListener {
            startActivity(Intent(requireContext(), FestsActivity::class.java))
        }

        view.findViewById<CardView>(R.id.cardNotices).setOnClickListener {
            startActivity(Intent(requireContext(), NoticesActivity::class.java))
        }

        view.findViewById<CardView>(R.id.cardNotifications).setOnClickListener {
            startActivity(Intent(requireContext(), NotificationActivity::class.java))
        }
    }

    private fun updateNotificationCount(view: View) {
        // This would typically come from your data source
        val unreadCount = 3
        view.findViewById<TextView>(R.id.textNotificationCount).text = unreadCount.toString()
    }
}