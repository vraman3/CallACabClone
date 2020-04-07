package com.example.callacabclone

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_requests.*
import android.text.method.TextKeyListener.clear
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.parse.*


data class RequestDataClass(val requestTitle: String)

class ViewRequestsActivity : AppCompatActivity() {

    val requestsAL:ArrayList<String> = ArrayList()

    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener

    private val requestDataObject = mutableListOf<RequestDataClass>(
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_requests)

        supportActionBar?.hide()
        requestDataObject.clear()
        requestDataObject.add(RequestDataClass("Getting nearby drivers"))

        requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        requestsRecyclerView.adapter = RequestAdapter(requestDataObject)

        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {

                Log.d("DEBUG", "onCreate, onLocationChanged")
                updateRequestListView(location)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

            override fun onProviderDisabled(provider: String?) {}

            override fun onProviderEnabled(provider: String?) {}
    }

    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
    } else {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListener
        )

        val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        if (lastKnownLocation != null) {
            Log.d("DEBUG", "onCreate ")
            updateRequestListView(lastKnownLocation)
        }
    }
}

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.toFloat(), locationListener)

                    var lastKnownLocation: Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                    Log.d("DEBUG","onRequestsPermissionsResult")
                    updateRequestListView(lastKnownLocation)
                }
            }
        }
    }

    fun updateRequestListView(location: Location) {
        if (location != null) {
            Log.d("DEBUG", "Update List View start")
            // Clear any earlier quests lists
            // Clearing here causes a delay in clearing the requests, and momentarily the list
            // is duplicated.
            //requests.clear();

            // Query nearby objects
            val nearbyObjectsQuery = ParseQuery.getQuery<ParseObject>("Request")

            val geoPointNearLocation =
                ParseGeoPoint(location.latitude, location.longitude)

            // set a where Near query
            nearbyObjectsQuery.whereNear("location", geoPointNearLocation)

            nearbyObjectsQuery.whereDoesNotExist("driverUsername")

            nearbyObjectsQuery.limit = 5

//            Log.d("DEBUG", "Before findInBackground Query")
            nearbyObjectsQuery.findInBackground(object : FindCallback<ParseObject> {
                override fun done(objects: List<ParseObject>, e: ParseException?) {
//                    Log.d("DEBUG", "Inside FindCallBack")
                    if (e == null) {

//                        Log.d("DEBUG", "No exceptions. Objects size " + objects.size)
                        // Try clearing requests here?


//                        Log.d("DEBUG", "requestDataObject was cleared. Current size: " + requestDataObject.size)
                        if (objects.isNotEmpty()) {

                            requestDataObject.clear()
//                            Log.d("DEBUG", "Objects are not empty")
                            // Clear any earlier quests lists
//                            requests.clear()
//                            requestLatitudes.clear()
//                            requestLongitudes.clear()

                            // Go through all the objects that were returned by query and add their
                            // distances to the request list
                            for (`object` in objects) {

                                val requestCurrentObjectLocation =
                                    `object`.get("location") as ParseGeoPoint?

                                if (requestCurrentObjectLocation != null) {

                                    val distanceInKms = geoPointNearLocation.distanceInKilometersTo(
                                        requestCurrentObjectLocation
                                    )

                                    val distanceOneDP =
                                        Math.round(distanceInKms * 10).toDouble() / 10


                                    requestDataObject.add(RequestDataClass(distanceOneDP.toString()
                                            + " kms, " + `object`.getString("username")))
                                    Log.d("DEBUG", "A RequestDataClass object was added. Current size: " + requestDataObject.size)


//                                    requests.add(
//                                        distanceOneDP.toString() + " kms, " + `object`.getString(
//                                            "username"
//                                        )
//                                    )
//
//                                    requestLatitudes.add(requestCurrentObjectLocation.latitude)
//                                    requestLongitudes.add(requestCurrentObjectLocation.longitude)
//                                    usernames.add(`object`.getString("username"))
                                }
                            }
                        } else {

                            requestDataObject.add(RequestDataClass("No students nearby..."))
                        }

//                        Log.d("DEBUG", "RecquestDataObject size: " + requestDataObject.size)
//                        requestsRecyclerView.adapter = RequestAdapter(requestDataObject)//?.notifyDataSetChanged()
                        requestsRecyclerView.adapter?.notifyDataSetChanged()
//                        arrayAdapter.notifyDataSetChanged()
                    }
                }
            })
        }

        Log.d("DEBUG", "Update List View end")
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
