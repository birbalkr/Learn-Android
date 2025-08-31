package com.recyclerview.project3.Myadapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.recyclerview.project3.Customer
import com.recyclerview.project3.MainActivity
import com.recyclerview.project3.R


class Myadapter(
    val customerArray: ArrayList<Customer>, val context: MainActivity
): RecyclerView.Adapter<Myadapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Myadapter.ViewHolder {
    val itemview= LayoutInflater.from(parent.context).inflate(R.layout.each_row,parent,false)
        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: Myadapter.ViewHolder, position: Int) {
        holder.text.text=customerArray[position].title
        holder.image.setImageResource(customerArray[position].image)
    }

    override fun getItemCount(): Int {
        return customerArray.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val text=itemView.findViewById<TextView>(R.id.text)
        val image=itemView.findViewById<ShapeableImageView>(R.id.image)
    }

}
