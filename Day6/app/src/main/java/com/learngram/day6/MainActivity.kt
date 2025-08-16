package com.learngram.day6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    companion object{
        const val KEY = "com.learngram.day6.MainActivity.KEY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btn = findViewById<Button>(R.id.btn)

        btn.setOnClickListener {
            val et1 = findViewById<EditText>(R.id.et1)
            val et2 = findViewById<EditText>(R.id.et2)
            val et3 = findViewById<EditText>(R.id.et3)
            val et4 = findViewById<EditText>(R.id.et4)

            val massage=et1.text.toString()+" "+et2.text.toString()+"\n"+et3.text.toString()+"\n"+et4.text.toString()

            intent= Intent(this,Order::class.java)
            intent.putExtra(KEY,massage)
            startActivity(intent)
        }
    }
}