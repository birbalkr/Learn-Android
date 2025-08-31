package com.recyclerview.project1

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recyclerview.project1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var recyclerView: RecyclerView
    lateinit var ListArray: ArrayList<CourseData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val courseList = listOf(
            CourseData("Javascript Fundamentals", "Chapter 1", "Introduction & Variables"),
            CourseData("Javascript Fundamentals", "Chapter 2", "Functions & Scope"),
            CourseData("Javascript Fundamentals", "Chapter 3", "Arrays & Objects"),
            CourseData("Javascript Fundamentals", "Chapter 4", "Callbacks & Closures"),
            CourseData("Javascript Fundamentals", "Chapter 4", "Callbacks & Closures"),
            CourseData("Javascript Fundamentals", "Chapter 4", "Callbacks & Closures"),
            CourseData("React Basics", "Chapter 1", "JSX & Components")

        )

        recyclerView = binding.recyclerView

        recyclerView.layoutManager= LinearLayoutManager(this)
        ListArray = arrayListOf<CourseData>()

        for (inex in courseList){
            val courseData = CourseData(inex.courseName,inex.chapter,inex.title)
            ListArray.add(courseData)
        }

        recyclerView.adapter= CourseAdapter(ListArray,this)



    }
}