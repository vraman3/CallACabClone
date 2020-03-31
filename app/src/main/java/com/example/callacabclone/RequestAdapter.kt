package com.example.callacabclone

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.request_list.view.*


class RequestAdapter(val items : ArrayList<String>, val context : Context) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_list, parent, false)

        val textView = rootView.requestTextView as TextView

        return MyViewHolder(rootView.requestTextView)
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

// Comment for commit
//    val requestItem = textView.request_item
//}