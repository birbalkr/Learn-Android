package com.api.e_cart.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.api.e_cart.MainActivity
import com.api.e_cart.R
import com.api.e_cart.api.AuthApi
import com.api.e_cart.databinding.ActivityUserDetailsBinding
import com.api.e_cart.model.auth.LoginResponse
import com.api.e_cart.model.auth.aboutMe.aboutMeData
import com.google.gson.stream.JsonToken
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class User_Details : AppCompatActivity() {
    lateinit var binding: ActivityUserDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val token = intent.getStringExtra("accessToken")
        val accessToken = token.toString()
        User_DetailsData(accessToken)

//        binding.tokenText.text=token.toString()

    }

    private fun User_DetailsData(token: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dummyjson.com/")
            .build().create(AuthApi::class.java)

        retrofit.getAbout("Bearer $token").enqueue(object : Callback<aboutMeData> {
            override fun onResponse(
                call: Call<aboutMeData?>,
                response: Response<aboutMeData?>
            ) {
                if (response.isSuccessful) {
                    Log.d("Data Respons", response.body().toString())

                    binding.FullName.text =
                        "Hello, " + response.body()?.firstName.toString() + " " + response.body()?.maidenName.toString() + " " + response.body()?.lastName.toString()
                    Picasso.get().load(response.body()?.image).into(binding.profileImage)
                    binding.emailId.text = response.body()?.email.toString()
                    binding.userName.text = response.body()?.username.toString()

                    // Store real password from API
                    var realPassword: String? = null
                    var isPasswordVisible = false

                    // After API response
                    realPassword = response.body()?.password.toString()
                    binding.passwd.text = "*******"

                    // Toggle visibility
                    binding.togglePassVisibility.setOnClickListener {
                        if (isPasswordVisible) {
                            // Hide password
                            binding.passwd.text = "*******"
                            binding.togglePassVisibility.setImageResource(R.drawable.hide)
                            isPasswordVisible = false
                        } else {
                            // Show password
                            binding.passwd.text = realPassword
                            binding.togglePassVisibility.setImageResource(R.drawable.eye)
                            isPasswordVisible = true
                        }
                    }

                    binding.phoneNumber.text = response.body()?.phone.toString()
                    binding.birthDate.text = response.body()?.birthDate.toString()
                    binding.bloodGroup.text = response.body()?.bloodGroup.toString()
                    binding.age.text = response.body()?.age.toString()
                    binding.gender.text = response.body()?.gender.toString()
                    binding.address.text = "Address: " + response.body()?.address?.address
                    binding.city.text = "City: " + response.body()?.address?.city
                    binding.state.text = "State: " + response.body()?.address?.state
                    binding.country.text = "Country: " + response.body()?.address?.country
                    binding.postcode.text = "Pin Code: " + response.body()?.address?.postalCode
                } else {
                    Log.d("Data Error", response.toString())
                }
            }

            override fun onFailure(
                call: Call<aboutMeData?>,
                t: Throwable
            ) {
                Log.d("Data Failure", t.toString())
            }
        })

        binding.logout.setOnClickListener {
            val intent = Intent(this, LoginUser::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}