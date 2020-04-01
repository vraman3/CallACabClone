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
//            .inflate(R.layout.request_list, parent, false)

//        val textView = rootView.requestTextView as TextView

        return MyViewHolder(rootView, parent)
//        return MyViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder?.requestItem?.text = items.get(position)
//        holder.textView.text = items[position]
    }
}

class MyViewHolder (inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.request_list, parent, false)){

    private lateinit var requestItemTextView:TextView

    init {

        requestItemTextView = itemView.requestTextView
    }
}

// Comment for commit
//    val requestItem = textView.request_item
//}