package com.example.callacabclone

import android.app.DownloadManager
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.request_list.view.*


class RequestAdapter(private val items : MutableList<RequestDataClass>)
    : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val rootView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.request_list, parent, false)

//        val textView = rootView.requestTextView as TextView
//        Log.d("DEBUG", "Inflating layout during onCreateViewHolder")
        return MyViewHolder(rootView, parent)
//        return MyViewHolder(textView)
    }

    override fun getItemCount(): Int {
        Log.d("DEBUG", "Request Data Object size:" + items.size.toString())
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val requestVar: RequestDataClass = items[position]
        holder.bind(requestVar)
//        holder?.requestItem?.text = items.get(position)
//        holder.textView.text = items[position]
    }
}

class MyViewHolder (inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.request_list, parent, false)){

    private var requestItemTextView:TextView

    init {
//        Log.d("DEBUG", "Init of MyViewHolder")
        requestItemTextView = itemView.requestTextView
    }

    fun bind(requestVar: RequestDataClass) {
//        Log.d("DEBUG", "Binding of request")
        requestItemTextView?.text = requestVar.requestTitle
    }
}

// Comment for commit
//    val requestItem = textView.request_item
//}