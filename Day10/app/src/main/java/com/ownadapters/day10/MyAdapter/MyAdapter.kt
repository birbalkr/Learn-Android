package com.ownadapters.day10.MyAdapter

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.ownadapters.day10.MainActivity
import com.ownadapters.day10.R
import com.ownadapters.day10.User

class MyAdapter(val context: MainActivity, val arrayList: ArrayList<User> ) :
    ArrayAdapter<User>(context, R.layout.listitem, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val view = inflater.inflate(R.layout.listitem, null)
        val user = arrayList[position]
        val image = view.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profile_image)
        val name =view.findViewById<android.widget.TextView>(R.id.name)
        val massage =view.findViewById<android.widget.TextView>(R.id.massage)
        val time =view.findViewById<android.widget.TextView>(R.id.time)

        name.text = arrayList[position].name
        massage.text = arrayList[position].massage
        time.text = arrayList[position].time
        image.setImageResource(arrayList[position].image)

        return view
    }

}
