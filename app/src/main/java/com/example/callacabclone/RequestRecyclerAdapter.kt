package com.example.callacabclone

import android.content.Intent
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RequestRecyclerAdapter(private val photos:ArrayList<ContactsContract.CommonDataKinds.Photo>) : RecyclerView.Adapter<PhotoHolder>(){

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = photos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val inflatedView = parent.inflate(R.layout.request_recyclerview_item_row, false)
        return PhotoHolder(inflatedView)
    }
}

// Class PhotoHolder
class PhotoHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

    private var view: View = v
    private var photo: ContactsContract.CommonDataKinds.Photo? = null

    init {
        v.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d("RecyclerView", "CLICK!")
        val context = itemView.context
        val showPhotoIntent = Intent(context, PhotoActivity::class.java)
    }

    companion object {
        private val PHOTO_KEY = "PHOTO"
    }
}