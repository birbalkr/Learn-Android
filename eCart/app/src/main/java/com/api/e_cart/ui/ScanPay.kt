package com.api.e_cart.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.api.e_cart.R
import com.api.e_cart.api.ImageApi
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ScanPay : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var spinner: Spinner
    private lateinit var textEnter: EditText
    private lateinit var textCorol: EditText
    private lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_scan_pay, container, false)

        imageView = view.findViewById(R.id.imageView)
        spinner=view.findViewById(R.id.dropdownSize)
        textEnter=view.findViewById(R.id.textEnter)     // ✅ input field
        button = view.findViewById(R.id.generateButton)
        textCorol=view.findViewById(R.id.backgroundColor)

        setupDropdown()
        setupButton()
        return view
    }

    private fun imagemake(size:String,text:String,textCorol:String){
        val retrofit= Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .build()
            .create(ImageApi::class.java)
        val response = retrofit.getImage(size,textCorol,"pacifico",text)
        response.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    val image = response.raw().request.url.toString()
                    Picasso.get().load(image).into(imageView)
                    Log.d("API_RESPONSE", "Image URL: $image")
                } else {
                    Log.e("API_ERROR", "Response not successful: ${response.code()}")
                }
            }
            override fun onFailure(
                call: Call<ResponseBody?>,
                t: Throwable
            ) {
                Log.e("API_ERROR", "Failure: ${t.message}")
            }

        })
    }

    private fun setupDropdown() {
        val items = listOf("200x200", "300x300", "400x400", "500x500", "600x600", "700x700")

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            items
        )
        spinner.adapter = adapter
    }
    private fun setupButton() {
        button.setOnClickListener {
            val selectedSize = spinner.selectedItem.toString()
            val enteredText = textEnter.text.toString().ifEmpty { "Enter Text" }
            val textCorol=textCorol.text.toString().ifEmpty { "black" }

            Toast.makeText(requireContext(), "Generating: $enteredText", Toast.LENGTH_SHORT).show()

            // ✅ Generate image with dropdown size + user text
            imagemake(selectedSize, enteredText,textCorol)
        }
    }


}