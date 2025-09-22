package com.api.e_cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.api.e_cart.ui.Account
import com.api.e_cart.ui.Cart
import com.api.e_cart.ui.Home
import com.api.e_cart.ui.Order
import com.api.e_cart.ui.ScanPay
import com.api.e_cart.ui.Setting
import com.api.e_cart.ui.Notifications
import com.api.e_cart.databinding.ActivityMainBinding
import com.api.e_cart.ui.User_Details
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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

//        data recvice
        val image = intent.getStringExtra("image")
        val accessToken = intent.getStringExtra("accessToken")

        Picasso.get().load(image).into(binding.imagelogo)

        binding.imagelogo.setOnClickListener {
            val intent = Intent(this, User_Details::class.java).apply {
                putExtra("accessToken", accessToken)
            }
            startActivity(intent)
        }


//        top bar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

//        bottomNavigation
        replaceFragment(Home())
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.account -> replaceFragment(Account())
                R.id.scanpay -> replaceFragment(ScanPay())
                R.id.order -> replaceFragment(Order())
                R.id.cart -> replaceFragment(Cart())
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.opt_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.notification -> {
                // Handle notification action
                replaceFragment(Notifications())
                return true
            }
            R.id.settings -> {
                replaceFragment(Setting())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}