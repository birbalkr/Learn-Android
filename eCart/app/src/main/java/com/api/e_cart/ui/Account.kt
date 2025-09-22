package com.api.e_cart.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.e_cart.adpater.postAdapter.PostAdapter
import com.api.e_cart.api.PostApi
import com.api.e_cart.databinding.FragmentAccountBinding
import com.api.e_cart.model.postData.PostData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Account : Fragment() {

    lateinit var binding: FragmentAccountBinding
    lateinit var recyclerView: RecyclerView
    lateinit var myAdpater: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAccountBinding.inflate(inflater, container, false)

fetchPost()
        return binding.root
    }

private fun fetchPost(){
    val retrofit= Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(PostApi::class.java)

    val response=retrofit.getPosts()

    response.enqueue(object : Callback<PostData>{
        override fun onResponse(
            call: Call<PostData?>,
            response: Response<PostData?>
        ) {
            if (response.isSuccessful){
                val responseBody = response.body()?.posts!!

                recyclerView=binding.recyclerView
                myAdpater= PostAdapter(requireContext(),responseBody)
                recyclerView.adapter=myAdpater
                recyclerView.layoutManager= LinearLayoutManager(context)
            }
            else{
                Log.d("Error",response.code().toString())
            }
        }

        override fun onFailure(
            call: Call<PostData?>,
            t: Throwable
        ) {
            Log.d("Error",t.message.toString())
        }

    })
}


}