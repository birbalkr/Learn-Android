package com.recyclerview.day9

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val list_view=findViewById<ListView>(R.id.list_view)

        val taskList = arrayListOf<String>();
        taskList.add("Task 1");
        taskList.add("Task 2");
        taskList.add("Task 3");
        taskList.add("Task 4");
        taskList.add("Task 5");


        val adapterForList = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskList);
        list_view.adapter = adapterForList;


        list_view.setOnItemClickListener { parent, view, position, id ->
            val text = "Clicked on Item : " + (view as TextView).text.toString()
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }

    }


}