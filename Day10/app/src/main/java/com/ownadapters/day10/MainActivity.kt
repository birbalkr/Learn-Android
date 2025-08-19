package com.ownadapters.day10

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ownadapters.day10.MyAdapter.MyAdapter

class MainActivity : AppCompatActivity() {

    lateinit var userArrayList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listView = findViewById<ListView>(R.id.list_view)

        val name= arrayOf("Ram", "Sita","Rahul", "Sumit","Ram", "Sita","Rahul", "Sumit")
        val lastmassege=arrayOf("Hey What is this?","Good Morning","Good Night","How are you","Hey What is this?","Good Morning","Good Night","How are you")
        val lasttime=arrayOf("6:25 AM","3:20 PM","9:00 AM","12:25 AM","6:25 AM","3:20 PM","9:00 AM","12:25 AM")
        val phoneNumber=arrayOf("9876543210","9876543210","9876543210","9876543210","9876543210","9876543210","9876543210","9876543210")
        var imageId= intArrayOf(R.drawable.profile,R.drawable.profile2,R.drawable.profile1,R.drawable.profile,R.drawable.profile,R.drawable.profile2,R.drawable.profile1,R.drawable.profile)

        userArrayList = ArrayList()

        for(eachIndex in name.indices){
            val user = User(name[eachIndex],lastmassege[eachIndex],lasttime[eachIndex],phoneNumber[eachIndex],imageId[eachIndex])
            userArrayList.add(user)
        }


        listView.isClickable = true
        listView.adapter = MyAdapter(this, userArrayList)

    }
}