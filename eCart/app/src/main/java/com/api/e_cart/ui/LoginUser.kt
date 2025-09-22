package com.api.e_cart.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.FontAssetDelegate
import com.api.e_cart.MainActivity
import com.api.e_cart.R
import com.api.e_cart.api.AuthApi
import com.api.e_cart.api.PostApi
import com.api.e_cart.databinding.ActivityLoginUserBinding
import com.api.e_cart.model.auth.LoginResponse
import com.api.e_cart.model.auth.UserAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginUser : AppCompatActivity() {
    private lateinit var binding: ActivityLoginUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        // Show form when "Login" button is clicked
        binding.showFormBtn.setOnClickListener {
            binding.loginImage.visibility = View.GONE
            binding.showFormBtn.visibility = View.GONE

            // Fade in the form for a smooth effect
            binding.loginForm.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).setDuration(500).start()
            }
        }

        // Handle login submit
        binding.loginBtn.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Logging in as $username", Toast.LENGTH_SHORT).show()
                // Add your actual login logic here
            }
        }

//        login butn
        binding.loginBtn.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                getUserData(username, password) // ✅ Pass entered credentials
            }
        }
    }

    private fun getUserData(username: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(AuthApi::class.java)

        // ✅ Create UserAuth request body
        val userAuth = UserAuth(username, password)

        // ✅ Call API
        retrofit.getLogin(userAuth).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        Toast.makeText(
                            this@LoginUser,
                            "Welcome ${it.firstName}",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@LoginUser, MainActivity::class.java).apply {
                            putExtra("image", it.image)
                            putExtra("accessToken", it.accessToken)
                        }
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Log.e("LoginError", "Error: ${response.errorBody()?.string()}")
                    Toast.makeText(this@LoginUser, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<LoginResponse?>,
                t: Throwable
            ) {
                Log.e("LoginFailure", "Error: ${t.message}")
                Toast.makeText(this@LoginUser, "Network error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}