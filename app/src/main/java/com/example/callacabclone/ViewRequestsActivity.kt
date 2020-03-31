package com.example.callacabclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_requests.*


class ViewRequestsActivity : AppCompatActivity() {

    val requestsAL:ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_requests)

        addText()

        requestsRecyclerView.layoutManager = LinearLayoutManager(this)

        requestsRecyclerView.adapter = RequestAdapter(requestsAL, this)
    }

    fun addText() {
        requestsAL.add("request 1")
        requestsAL.add("request 2")
        requestsAL.add("request 3")
        requestsAL.add("request 4")
        requestsAL.add("request 5")
        requestsAL.add("request 6")
    }
}


