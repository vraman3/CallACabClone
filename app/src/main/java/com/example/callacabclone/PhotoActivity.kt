package com.example.callacabclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PhotoActivity : AppCompatActivity() {

    private var selectedPhoto: Photo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        selectedPhoto = intent.getSerializableExtra(PHOTO_KEY) as Photo
        
    }

    companion object {
        private val PHOTO_KEY = "PHOTO"
    }
}
