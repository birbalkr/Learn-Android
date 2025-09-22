package com.API.day15

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import kotlin.Double
import kotlin.toString

class MyProductAdpter(val context: MainActivity, val productList: List<Product>) :
    RecyclerView.Adapter<MyProductAdpter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyProductAdpter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val currentNews = productList[position]
        Log.d("product", productList.toString())
        holder.txname.text = currentNews.title
        holder.txdec.text = currentNews.description
        holder.txprice.text = String.format("₹%.2f", currentNews.price)
        holder.txrating.text = String.format("%.1f ★", currentNews.rating)
        Picasso.get().load(currentNews.thumbnail).into(holder.image)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ShapeableImageView>(R.id.image)
        val txname = itemView.findViewById<TextView>(R.id.name)
        val txdec = itemView.findViewById<TextView>(R.id.dec)
        var txprice = itemView.findViewById<TextView>(R.id.price)
        var txrating = itemView.findViewById<TextView>(R.id.rating)

    }
}