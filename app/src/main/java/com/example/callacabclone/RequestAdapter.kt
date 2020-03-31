package com.example.callacabclone

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RequestAdapter(val items : ArrayList<String>, val context : Context) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_list, parent, false) as TextView

//        val textView = rootView.requestTextView
        // Comment for commit
        return MyViewHolder(rootView)
//        return MyViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder?.requestItem?.text = items.get(position)
        holder.textView.text = items[position]
    }
}

class MyViewHolder (val textView: TextView) : RecyclerView.ViewHolder(textView)// {
//    val requestItem = textView.request_item
//}