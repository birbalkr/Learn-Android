package com.recyclerview.project2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recyclerview.project2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var recyclerView: RecyclerView
    lateinit var Listarray: ArrayList<Course>


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
            Course("Javascript Fundamentals", "Introduction & Variables"),
            Course("Javascript Fundamentals", "Introduction & Variables"),
            Course("Javascript Fundamentals", "Functions & Scope"),
            Course("Javascript Fundamentals", "Functions & Scope"),
            Course("Javascript Fundamentals", "Arrays & Objects"),
            Course("Javascript Fundamentals", "Callbacks & Closures"),
            Course("Javascript Fundamentals", "Callbacks & Closures"),
            Course("Javascript Fundamentals", "Callbacks & Closures"),
            Course("React Basics", "JSX & Components")

        )

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        Listarray = arrayListOf<Course>()

        for (index in courseList) {
            val course = Course(index.title, index.description)
            Listarray.add(course)
        }

        recyclerView.adapter = CourseAdapter(Listarray, this)
    }
}