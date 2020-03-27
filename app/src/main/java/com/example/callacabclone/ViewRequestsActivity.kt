package com.example.callacabclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_requests.*

class ViewRequestsActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RequestRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_requests)
        linearLayoutManager = LinearLayoutManager(this)
        requestsRecyclerView.layoutManager = linearLayoutManager
//        adapter = RequestRecyclerAdapter(photoList)
    }
}
