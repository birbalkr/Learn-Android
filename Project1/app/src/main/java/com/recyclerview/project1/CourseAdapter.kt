package com.recyclerview.project1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CourseAdapter(val courseList: List<CourseData>, val context: MainActivity) :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseName = itemView.findViewById<TextView>(R.id.courseName)
        val courseChapter = itemView.findViewById<TextView>(R.id.chapter)
        val courseTitle = itemView.findViewById<TextView>(R.id.chapterName)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseAdapter.CourseViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseAdapter.CourseViewHolder, position: Int) {
        val currentItem = courseList[position]
        holder.courseName.text = currentItem.courseName
        holder.courseChapter.text = currentItem.chapter
        holder.courseTitle.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return courseList.size
    }
}