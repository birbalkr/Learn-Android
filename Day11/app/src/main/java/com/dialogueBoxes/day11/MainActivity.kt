package com.dialogueBoxes.day11

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dialogueBoxes.day11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
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

        binding.btn.setOnClickListener {
            val BasicDialogueBox = AlertDialog.Builder(this)
            BasicDialogueBox.setTitle("This is Alert Dialogue Box")
            BasicDialogueBox.setMessage("Do you want to exit")
            BasicDialogueBox.setIcon(R.drawable.outline_arrow_back_ios_24)
            BasicDialogueBox.setPositiveButton("Yes", DialogInterface.OnClickListener({ dialog, which ->
                finish()
            }))
            BasicDialogueBox.setNegativeButton("No", DialogInterface.OnClickListener({ dialog, which ->
                dialog.cancel()
            }))
            BasicDialogueBox.setNeutralButton("Cancel", DialogInterface.OnClickListener({ dialog, which ->
                dialog.cancel()
            }))
            BasicDialogueBox.show()
        }

        binding.btn1.setOnClickListener {
            var options =arrayOf("Option1","Option2","Option3")
            var CustomDialogueBox = AlertDialog.Builder(this)
            CustomDialogueBox.setTitle("This is single Choice alert Dialogue Box")
            CustomDialogueBox.setSingleChoiceItems(options,0,DialogInterface.OnClickListener({ dialog, which ->
                Toast.makeText(this,"${options[which]}",Toast.LENGTH_SHORT).show()
            }))
            CustomDialogueBox.setPositiveButton("Yes",DialogInterface.OnClickListener({ dialog, which ->

            }))
            CustomDialogueBox.setNegativeButton("No",DialogInterface.OnClickListener({ dialog, which ->
                dialog.cancel()
            }))
            CustomDialogueBox.show()
        }

        binding.btn2.setOnClickListener {
            var options =arrayOf("Option1","Option2","Option3")
            var CustomDialogueBox = AlertDialog.Builder(this)
            CustomDialogueBox.setTitle("This is multi Choice alert Dialogue Box")
            CustomDialogueBox.setMultiChoiceItems(options,null,DialogInterface.OnMultiChoiceClickListener({ dialog, which, isChecked ->
                Toast.makeText(this,"${options[which]}",Toast.LENGTH_SHORT).show()
            }))
            CustomDialogueBox.setPositiveButton("Yes",DialogInterface.OnClickListener({ dialog, which ->

            }))
            CustomDialogueBox.setNegativeButton("No",DialogInterface.OnClickListener({ dialog, which ->
                dialog.cancel()
            }))
            CustomDialogueBox.show()
        }



    }
}