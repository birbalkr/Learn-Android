package com.api.e_cart.adpater

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.api.e_cart.R
import com.api.e_cart.model.Product
import com.squareup.picasso.Picasso

class MyAdpater(val context: Context, val productList: List<Product>) :
    RecyclerView.Adapter<MyAdpater.MyViewHolder>() {

    //   passing Data
    lateinit var myListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        myListener = listener
    }

    //    end
    class MyViewHolder(itemView: View, myListener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView2)!!
        val title = itemView.findViewById<TextView>(R.id.productTitle)!!
        val price = itemView.findViewById<TextView>(R.id.productPrice)!!
        val strikeText = itemView.findViewById<TextView>(R.id.STRIKETEXT)!!
        val discount = itemView.findViewById<TextView>(R.id.discount)!!

        init {
            itemView.setOnClickListener {
                myListener.onItemClick(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return MyViewHolder(itemview, myListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.strikeText.paintFlags =
            holder.strikeText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        val currentItem = productList[position]
        holder.title.text = currentItem.title
        holder.strikeText.text = String.format("$%.2f", currentItem.price + currentItem.discountPercentage+30)
        holder.price.text = String.format("$%.2f  ", currentItem.price + (currentItem.discountPercentage))
        holder.discount.text = String.format("$%.2f", currentItem.price)

        Picasso.get().load(currentItem.thumbnail).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}