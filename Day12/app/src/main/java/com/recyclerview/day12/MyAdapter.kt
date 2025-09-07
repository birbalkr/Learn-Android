package com.recyclerview.day12

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class MyAdapter(val newaArray: ArrayList<News>, val context: MainActivity):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyAdapter.MyViewHolder {
        val itemView  = LayoutInflater.from(parent.context).inflate(R.layout.each_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
            val currentNews = newaArray[position]
        holder.headingTitle.text = currentNews.newsHeading
        holder.headingImage.setImageResource(currentNews.newsImage)
    }

    override fun getItemCount(): Int {
        return newaArray.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headingTitle = itemView.findViewById<TextView>(R.id.headingTitle)
        val headingImage = itemView.findViewById<ShapeableImageView>(R.id.headingImage)
    }
}