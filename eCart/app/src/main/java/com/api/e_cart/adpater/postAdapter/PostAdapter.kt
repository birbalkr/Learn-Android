package com.api.e_cart.adpater.postAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.api.e_cart.R
import com.api.e_cart.model.postData.Post

class PostAdapter(val context: Context, val postData: List<Post>):
RecyclerView.Adapter<PostAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)!!
        val body = itemView.findViewById<TextView>(R.id.body)!!
        val like = itemView.findViewById<TextView>(R.id.like)!!
        val dislike = itemView.findViewById<TextView>(R.id.dislike)!!
        val view = itemView.findViewById<TextView>(R.id.view)!!
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_post, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val currentItem = postData[position]
        holder.title.text = currentItem.title
        holder.body.text = currentItem.body
        holder.like.text = currentItem.reactions.likes.toString()
        holder.dislike.text = currentItem.reactions.dislikes.toString()
        holder.view.text = currentItem.views.toString()
    }


    override fun getItemCount(): Int {
       return postData.size
    }

}
