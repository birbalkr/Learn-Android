package com.api.e_cart

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.api.e_cart.databinding.ActivityProductDetailsBinding
import com.api.e_cart.model.Review
import com.squareup.picasso.Picasso

class ProductDetails : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title = intent.getStringExtra("title")
        val price = intent.getStringExtra("price")
        val discount = intent.getStringExtra("discount")
        val thumbnail = intent.getStringExtra("thumbnail")
        val description = intent.getStringExtra("description")
        val rating = intent.getStringExtra("rating")



        Picasso.get().load(thumbnail).into(binding.image)
        binding.title.text = title
        binding.oldPrice.paintFlags = binding.oldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        binding.oldPrice.text = String.format("₹%.2f", price!!.toDouble() + discount!!.toDouble() + 30)
        binding.newPrice.text = String.format("₹%.2f", price.toDouble())
        binding.rating.text = rating
        binding.description.text = description



//        Stars code
        val ratingValue = rating?.toDoubleOrNull() ?: 0.0
        val stars = ratingValue.toInt() // or roundToInt()
        for (i in 0 until stars) {
            val starLottieView = LottieAnimationView(this).apply {
                setAnimation(R.raw.star)
                repeatCount = 0
                playAnimation()
                layoutParams = LinearLayout.LayoutParams(60, 60).apply {
                    setMargins(0, 0, -15, 0)
                }
            }
            binding.starContainer.addView(starLottieView)
        }

//        review list
        val reviews = intent.getParcelableArrayListExtra<Review>("reviews")
        if (reviews != null) {
            val builder = SpannableStringBuilder()

            for (review in reviews) {
                val startName = builder.length
                builder.append("${review.reviewerName} ${"⭐".repeat(review.rating)}\n")
                builder.setSpan(
                    AbsoluteSizeSpan(16,true),  // 16sp
                    startName,
                    builder.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                builder.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startName,
                    builder.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                val startEmail = builder.length
                builder.append("${review.reviewerEmail}\n")
                builder.setSpan(
                    AbsoluteSizeSpan(12, true),  // 12sp
                    startEmail,
                    builder.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                builder.append(" ")
                val startComments = builder.length
                builder.append("${review.comment}\n\n")
                builder.setSpan(
                    AbsoluteSizeSpan(12, true),  // 12sp
                    startComments,
                    builder.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            binding.reviewText.text = builder
        }


    }
}