package com.example.callacabclone

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.request_recyclerview_item_row.view.*

class RequestRecyclerAdapter(private val photos:ArrayList<Photo>) : RecyclerView.Adapter<PhotoHolder>(){

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val itemPhoto = photos[position]
        holder.bindPhoto(itemPhoto)
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
    private var photo: Photo? = null

    init {
        v.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Log.d("RecyclerView", "CLICK!")
        val context = itemView.context
        val showPhotoIntent = Intent(context, PhotoActivity::class.java)
        showPhotoIntent.putExtra(PHOTO_KEY, photo)
        context.startActivity(showPhotoIntent)
    }

    fun bindPhoto(photo: Photo) {
        this.photo = photo
        Picasso.with(view.context).load(photo.url).into(view.itemImage)
        view.itemDate.text = photo.humanDate
        view.itemDescription.text = photo.explanation
    }

    companion object {
        private val PHOTO_KEY = "PHOTO"
    }
}