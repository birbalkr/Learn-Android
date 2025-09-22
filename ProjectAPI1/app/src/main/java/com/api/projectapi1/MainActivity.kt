package com.api.projectapi1

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieDrawable
import com.api.projectapi1.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ask for location permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        } else {
            getCurrentCity()
        }

        SearchCity()
    }

    // Handle permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentCity()
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            fetchWeatherData("Delhi") // fallback city
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentCity() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(this, Locale.getDefault())
                val addresses: List<Address?>? =
                    geocoder.getFromLocation(location.latitude, location.longitude, 1)

                if (addresses != null && addresses.isNotEmpty()) {
                    val cityName = addresses[0]?.locality ?: "Delhi"
                    Toast.makeText(this, "Current City: $cityName", Toast.LENGTH_SHORT).show()
                    fetchWeatherData(cityName)
                }
            } else {
                Toast.makeText(this, "Unable to get location", Toast.LENGTH_SHORT).show()
                fetchWeatherData("Delhi") // fallback
            }
        }
    }

    private fun SearchCity() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
    }

    private fun fetchWeatherData(cityName: String) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)

        val response = retrofitBuilder.getWeatherData(
            cityName,
            "73be50bc1261c2d99c542403000d3330", // your API key
            "metric"
        )

        response.enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val temp = responseBody.main.temp
                    val maxTemp = responseBody.main.temp_max
                    val minTemp = responseBody.main.temp_min
                    val humidity = responseBody.main.humidity
                    val windSpeed = responseBody.wind.speed
                    val sunRise =
                        (responseBody.sys.sunrise + responseBody.timezone) * 100L
                    val sunSet =
                        (responseBody.sys.sunset + responseBody.timezone) * 100L
                    val seaLevel = responseBody.main.pressure
                    val condition = responseBody.weather.firstOrNull()?.main

                    when (condition?.lowercase()) {
                        "clear sky", "sunny", "clear" -> {
                            binding.lottieAnimationView.setAnimation(R.raw.sun)
                            binding.lottieAnimationView.repeatCount = LottieDrawable.INFINITE
                            binding.lottieAnimationView.playAnimation()
                            binding.main.setBackgroundResource(R.drawable.sunny_background)
                        }

                        "partly clouds", "clouds", "overcast", "mist", "foggy" -> {
                            binding.lottieAnimationView.setAnimation(R.raw.cloud)
                            binding.lottieAnimationView.repeatCount = LottieDrawable.INFINITE
                            binding.lottieAnimationView.playAnimation()
                            binding.main.setBackgroundResource(R.drawable.colud_background)
                        }

                        "light rain", "drizzle", "moderate rain", "showers", "heavy rain", "rain" -> {
                            binding.lottieAnimationView.setAnimation(R.raw.rain)
                            binding.lottieAnimationView.repeatCount = LottieDrawable.INFINITE
                            binding.lottieAnimationView.playAnimation()
                            binding.main.setBackgroundResource(R.drawable.rain_background)
                        }

                        "light snow", "moderate snow", "heavy snow", "blizzard" -> {
                            binding.lottieAnimationView.setAnimation(R.raw.snow)
                            binding.lottieAnimationView.repeatCount = LottieDrawable.INFINITE
                            binding.lottieAnimationView.playAnimation()
                            binding.main.setBackgroundResource(R.drawable.snow_background)
                        }

                        else -> {
                            binding.lottieAnimationView.setAnimation(R.raw.sun)
                            binding.lottieAnimationView.repeatCount = LottieDrawable.INFINITE
                            binding.lottieAnimationView.playAnimation()
                            binding.main.setBackgroundResource(R.drawable.sunny_background)
                        }
                    }

                    // Fill UI
                    binding.temp.text = "$temp °C"
                    binding.cityName.text = responseBody.name
                    binding.weather.text = condition
                    binding.maxTemp.text = "Max: $maxTemp °C"
                    binding.minTemp.text = "Min: $minTemp °C"
                    binding.humidity.text = "$humidity %"
                    binding.windSpeed.text = "$windSpeed m/s"

                    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                    binding.sunrise.text = sdf.format(Date(sunRise))
                    binding.sunset.text = sdf.format(Date(sunSet))

                    binding.sea.text = seaLevel.toString()
                    binding.condition.text = condition
                    binding.day.text = dayName(System.currentTimeMillis())
                    binding.date.text = date()
                }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                t.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("TAG", "onFailure: ${t.message}")
                }
            }
        })
    }

    private fun date(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun dayName(timestamp: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}
