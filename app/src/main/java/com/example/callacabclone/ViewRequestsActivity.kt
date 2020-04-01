package com.example.callacabclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_requests.*

data class RequestDataClass(val requestTitle: String)

class ViewRequestsActivity : AppCompatActivity() {

    val requestsAL:ArrayList<String> = ArrayList()

    private val requestDataObject = listOf<RequestDataClass>(
        RequestDataClass("Request CR3"),
        RequestDataClass("Request E3X"),
        RequestDataClass("Request 3LP"),
        RequestDataClass("Request KL1"),
        RequestDataClass("Request 531"),
        RequestDataClass("Request 3Q5"),
        RequestDataClass("Request x25"),
        RequestDataClass("Request 5GF")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_requests)

//        addText()

        requestsRecyclerView.layoutManager = LinearLayoutManager(this)

        requestsRecyclerView.adapter = RequestAdapter(requestDataObject)
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
