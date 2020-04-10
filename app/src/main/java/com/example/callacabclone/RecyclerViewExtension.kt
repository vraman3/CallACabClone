package com.example.callacabclone

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

//interface OnItemClickListener {
//    fun onItemClicked(position: Int, view: View)
//}
//
//fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
//    Log.d("DEBUG", "addOnItemClickListener")
//    this.addOnChildAttachStateChangeListener(object: RecyclerView.OnChildAttachStateChangeListener {
//        override fun onChildViewDetachedFromWindow(view: View) {
//            Log.d("DEBUG", "onChild view detached")
//            view?.setOnClickListener(null)
//        }
//
//        override fun onChildViewAttachedToWindow(view: View) {
//            Log.d("DEBUG", "onChild view ATTAched")
//            view?.setOnClickListener({
//                val holder = getChildViewHolder(view)
//                onClickListener.onItemClicked(holder.adapterPosition, view)
//            })
//        }
//    })
//}