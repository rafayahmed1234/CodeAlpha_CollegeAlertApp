package com.example.collegealertapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExamsAdapter(private var exams: List<ExamItem>) : RecyclerView.Adapter<ExamsAdapter.ExamViewHolder>() {

    class ExamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subject: TextView = view.findViewById(R.id.examSubject)
        val type: TextView = view.findViewById(R.id.examType)
        val dateTime: TextView = view.findViewById(R.id.examDateTime)
        val venue: TextView = view.findViewById(R.id.examVenue)
        val duration: TextView = view.findViewById(R.id.examDuration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exam, parent, false)
        return ExamViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        val exam = exams[position]
        holder.subject.text = exam.subject ?: "No Subject"
        holder.type.text = exam.type ?: "N/A"
        holder.dateTime.text = "${exam.date ?: "TBA"} • ${exam.time ?: ""}"
        holder.venue.text = "Venue: ${exam.venue ?: "Not available"}"
        holder.duration.text = "Duration: ${exam.duration ?: "Not specified"}"
    }

    override fun getItemCount() = exams.size

    fun updateData(newExamsList: List<ExamItem>) {
        this.exams = newExamsList
        notifyDataSetChanged()
    }
}