package com.example.callacabclone

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.location.LocationManager
import android.location.Location
import android.location.LocationListener
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.parse.*
import com.parse.ParseObject
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class RiderActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var locationManager: LocationManager
    lateinit var locationListener: LocationListener
    lateinit var callCabButton: Button
    var requestActive: Boolean = false
    //var locationManager: LocationManager? = null
    //var locationListener: LocationListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rider)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.riderActivityLayout) as SupportMapFragment
        mapFragment.getMapAsync(this)

        callCabButton = findViewById<Button>(R.id.callCabButton)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        LatLng userLocation = new LatLng()
        locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = object: LocationListener {
            override fun onLocationChanged(location: Location) {
                updateMap(location)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

            override fun onProviderDisabled(provider: String?) {}

            override fun onProviderEnabled(provider: String?) {}
        }

        if (ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)

            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if (lastKnownLocation != null) {
                updateMap(lastKnownLocation)
            }
        }
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.toFloat(), locationListener)

                    var lastKnownLocation: Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                    updateMap(lastKnownLocation)
                }
            }
        }
    }

    fun callCab(view: View) {

        Log.d("Debug","Call a cab")

        var requestColumnName = "Request"
        if(requestActive) {
            var requestActiveQuery = ParseQuery<ParseObject>(requestColumnName)
            requestActiveQuery.whereEqualTo("username", ParseUser.getCurrentUser().username)

            Log.d("DEBUG",ParseUser.getCurrentUser().username)

            var results = requestActiveQuery.find()
            var counter = results.size

            if(results.isNotEmpty()) {
                for(`result` in results) {
                    `result`.deleteInBackground{
                        Log.d("DEBUG", "Previous request deleted!")
                        requestActive = false
                        callCabButton.text = "Call Cab"
                    }
                }
            }
//            requestActiveQuery.findInBackground(object: FindCallback<ParseObject> {
//                override fun done(objects: MutableList<ParseObject>, e: ParseException) {
//                    Log.d("DEBUG", e.toString())
//                    if(e == null) {
//                        Log.d("DEBUG", "No exception!")
//                        if(objects.isNotEmpty()) {
//                            Log.d("DEBUG", "Objects exist!")
//                            for (`object` in objects) {
//                                `object`.deleteInBackground {
//                                    Log.d("DEBUG", "Previous request deleted!")
//                                }
//                            }
//                            requestActive = false
//                            callCabButton.text = "Call Cab"
//                        }
//                    }else {
//                        e.printStackTrace()
//                    }
//                }
//            })


        }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0.toFloat(),
                locationListener
            )

            var lastKnownLocation: Location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if (lastKnownLocation != null) {
                val request = ParseObject(requestColumnName)
                request.put("username", ParseUser.getCurrentUser().username)

                val parseGeoPoint: ParseGeoPoint =
                    ParseGeoPoint(lastKnownLocation.latitude, lastKnownLocation.longitude)

                request.put("location", parseGeoPoint)

                request.saveInBackground(object : SaveCallback {
                    override fun done(e: ParseException?) {
                        callCabButton.text = "Cancel Cab"
                        requestActive = true
                    }
                })
            } else {
                Toast.makeText(this, "Could not find location. Try again later", Toast.LENGTH_SHORT)
            }
        }
    }

    fun updateMap(location: Location) {

//        Log.d("DEBUG", "TEST LOCATION")
        val userLocation = LatLng(location.latitude,location.longitude)

        mMap.clear()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15.toFloat()))
        mMap.addMarker(MarkerOptions().position(userLocation).title("You are here!"))
    }

    fun homeRider(view: View){
        //ParseUser.logOut()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun logoutRider(view: View){
        //ParseUser.logOut()

        //val intent = Intent(this, MainActivity::class.java)
        //startActivity(intent)
    }
}
