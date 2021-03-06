package com.example.callacabclone

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.callacabclone.databinding.ActivityViewRequestsBinding
import com.parse.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestDataClass(val requestTitle: String, val requestLatitude: Double = 0.0, val requestLongitude: Double = 0.0, val username: String = "N/A") : Parcelable

//data class RequestDataClass(val requestTitle: String)

class ViewRequestsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewRequestsBinding
    val requestsAL:ArrayList<String> = ArrayList()
    var requestLatitude:ArrayList<Float> = ArrayList()
    var requestLongitude:ArrayList<Float> = ArrayList()

    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener

    private val requestDataObject = mutableListOf<RequestDataClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_requests)

        supportActionBar?.hide()
        requestDataObject.clear()
        requestDataObject.add(RequestDataClass("Getting nearby drivers"))

        binding.requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.requestsRecyclerView.adapter = RequestAdapter(requestDataObject, RequestListener {requestTitle, requestPosition ->

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

                val lastKnownLocation =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                val intent = Intent(applicationContext, DriverActivity::class.java)
                intent.putExtra("request_title", requestTitle.toString())

                intent.putExtra("request_object", requestDataObject[requestPosition]);
                intent.putExtra("driver_latitude", lastKnownLocation.latitude);
                intent.putExtra("driver_longitude", lastKnownLocation.longitude);

                Log.d("DEBUG", "Redirecting to driver activity page")
                startActivity(intent)
            }

//            Log.d("DEBUG", "View Activity onClick()" + requestTitle.toString())[
//            Toast.makeText(applicationContext,"Item no. " + requestTitle.toString() + " at position $requestPosition", Toast.LENGTH_SHORT ).show()


        })


        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {

//                Log.d("DEBUG", "onCreate, onLocationChanged")
//                updateRequestListView(location)
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
//            Log.d("DEBUG", "onCreate ")
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

//                    Log.d("DEBUG","onRequestsPermissionsResult")
                    updateRequestListView(lastKnownLocation)
                }
            }
        }
    }

    fun updateRequestListView(location: Location) {
        if (location != null) {
//            Log.d("DEBUG", "Update List View start")
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

                                    val requestDataClassToAdd = `object`.getString("username")?.let { username ->
                                        RequestDataClass(
                                            distanceOneDP.toString() + " kms",
                                            requestCurrentObjectLocation.latitude,
                                            requestCurrentObjectLocation.longitude,
                                            username
                                        )}

                                    if (requestDataClassToAdd != null) {
                                        requestDataObject.add(requestDataClassToAdd)
                                    }
//                                    requestDataObject.add(RequestDataClass(distanceOneDP.toString()
//                                            + " kms, " + `object`.getString("username")))
//                                    Log.d("DEBUG", "A RequestDataClass object was added. Current size: " + requestDataObject.size)


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
                        binding.requestsRecyclerView.adapter?.notifyDataSetChanged()
//                        arrayAdapter.notifyDataSetChanged()
                    }
                }
            })
        }

//        Log.d("DEBUG", "Update List View end")
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
