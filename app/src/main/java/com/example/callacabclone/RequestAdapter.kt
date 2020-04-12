package com.example.callacabclone

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.callacabclone.databinding.RequestListBinding
import kotlinx.android.synthetic.main.request_list.view.*

class RequestListener(val clickListener: (requestTitle: String?) -> Unit) {
    fun onClick(requestVar: RequestDataClass?) = clickListener(requestVar?.requestTitle)
}

class RequestAdapter(private val items : MutableList<RequestDataClass>, val clickListener: RequestListener)
    : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
//        Log.d("DEBUG", "Request Data Object size:" + items.size.toString())
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val requestVar: RequestDataClass = items[position]
        holder.bind(requestVar, clickListener)
    }
}

class MyViewHolder (val binding: RequestListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var requestItemTextView: TextView

    init {
//        Log.d("DEBUG", "Init of MyViewHolder")
        requestItemTextView = itemView.requestTextView
    }

    fun bind(requestVar: RequestDataClass?, clickListener: RequestListener) {
        requestItemTextView?.text = requestVar?.requestTitle

        binding.request = requestVar
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): MyViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RequestListBinding.inflate(layoutInflater, parent, false)
            return MyViewHolder(binding)

        }
    }
}