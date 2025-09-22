package com.api.e_cart.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.e_cart.ProductDetails
import com.api.e_cart.databinding.FragmentHomeBinding
import com.api.e_cart.api.ApiProduct
import com.api.e_cart.adpater.MyAdpater
import com.api.e_cart.model.Product
import com.api.e_cart.model.ProductData
import com.api.e_cart.model.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Home : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var binding: FragmentHomeBinding
    lateinit var myProductAdpter: MyAdpater

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        fetchProduct()
        setupSearchBar()
        return binding.root
    }

    private fun fetchProduct() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiProduct::class.java)
        val response = retrofit.getProduct()
        response.enqueue(object : Callback<ProductData> {
            override fun onResponse(
                call: Call<ProductData?>,
                response: Response<ProductData?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val productList = responseBody?.products!!

                    recyclerView = binding.recyclerView
                    myProductAdpter = MyAdpater(requireContext(), productList)
                    recyclerView.adapter = myProductAdpter

                    myProductAdpter.setOnItemClickListener(object : MyAdpater.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(context, ProductDetails::class.java)
                            intent.putExtra("title", productList[position].title)
                            intent.putExtra("price", productList[position].price.toString())
                            intent.putExtra(
                                "discount", productList[position].discountPercentage.toString()
                            )
                            intent.putExtra("thumbnail", productList[position].thumbnail)
                            intent.putExtra("description", productList[position].description)
                            intent.putExtra("brand", productList[position].brand)
                            intent.putExtra("category", productList[position].category)
                            intent.putExtra("stock", productList[position].stock.toString())
                            intent.putExtra("rating", productList[position].rating.toString())
                            intent.putParcelableArrayListExtra(
                                "reviews",
                                ArrayList<Review>(productList[position].reviews) // explicit type
                            )
                            startActivity(intent)
                        }
                    })
                    val displayMetrics = resources.displayMetrics
                    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
                    val spanCount = when {
                        screenWidthDp < 480 -> 3   // small screens
                        screenWidthDp < 720 -> 4   // medium screens
                        else -> 6                  // large screens
                    }
                    recyclerView.layoutManager = GridLayoutManager(context, spanCount,GridLayoutManager.VERTICAL, false)


                } else {
                    Log.e("API_ERROR", "Response not successful: ${response.code()}")
                }

            }


            override fun onFailure(
                call: Call<ProductData?>,
                t: Throwable
            ) {
                Toast.makeText(context, "Something went wrong ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }
    //    Search
    private fun setupSearchBar() {
        val searchBar = binding.searchView
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchProduct(newText)
                }
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

        })

    }
    private fun searchProduct(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiProduct::class.java)

        val response = retrofit.searchProduct(query)
        response.enqueue(object : Callback<ProductData> {
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                if (response.isSuccessful) {
                    val searchResults = response.body()?.products ?: emptyList()
                    setupRecyclerView(searchResults)
                }
            }

            override fun onFailure(call: Call<ProductData>, t: Throwable) {
                Toast.makeText(context, "Search failed: $t", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setupRecyclerView(productList: List<Product>) {
        recyclerView = binding.recyclerView
        myProductAdpter = MyAdpater(requireContext(), productList)
        recyclerView.adapter = myProductAdpter
        myProductAdpter.setOnItemClickListener(object : MyAdpater.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val product = productList[position]
                val intent = Intent(context, ProductDetails::class.java).apply {
                    putExtra("title", product.title)
                    putExtra("price", product.price.toString())
                    putExtra("discount", product.discountPercentage.toString())
                    putExtra("thumbnail", product.thumbnail)
                    putExtra("description", product.description)
                    putExtra("brand", product.brand)
                    putExtra("category", product.category)
                    putExtra("stock", product.stock.toString())
                    putExtra("rating", product.rating.toString())
                    putParcelableArrayListExtra("reviews", ArrayList(product.reviews))
                }
                startActivity(intent)
            }
        })

        val displayMetrics = resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        val spanCount = when {
            screenWidthDp < 480 -> 3
            screenWidthDp < 720 -> 4
            else -> 6
        }

        recyclerView.layoutManager = GridLayoutManager(context, spanCount)
    }
}