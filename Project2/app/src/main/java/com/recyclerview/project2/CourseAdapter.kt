package com.recyclerview.project2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(val courseList: List<Course>, val context: MainActivity) :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    class CourseViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title_text)
        val description = itemView.findViewById<TextView>(R.id.text2)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseAdapter.CourseViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.each_row, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseAdapter.CourseViewHolder, position: Int) {
        val currentItem = courseList[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return courseList.size
    }
}