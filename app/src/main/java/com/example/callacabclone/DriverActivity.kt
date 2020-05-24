package com.example.callacabclone

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class DriverActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        intent = getIntent()

        val markers: ArrayList<Marker> = ArrayList()

        var tmpString: String = intent.getStringExtra("request_title")
        var incomingRequestObject: RequestDataClass = intent.getParcelableExtra("request_object")

        val driverLocation = LatLng(
            intent.getDoubleExtra("driver_latitude", 0.0),
            intent.getDoubleExtra("driver_longitude", 0.0)
        )

        val requestLocation = LatLng( incomingRequestObject.requestLatitude, incomingRequestObject.requestLongitude )
        Log.d("DEBUG", "driver: $driverLocation,  rider: $requestLocation")
        markers.add(mMap.addMarker(MarkerOptions().position(driverLocation).title("Your Location")))
        markers.add(mMap.addMarker(MarkerOptions().position(requestLocation).title("Request Location")))

        val builder = LatLngBounds.Builder()
        for (marker in markers) {
            builder.include(marker.position)
        }
        val bounds: LatLngBounds = builder.build()

        val width: Int = resources.displayMetrics.widthPixels
        val height: Int = resources.displayMetrics.heightPixels
        val padding: Int = (height * 0.20).toInt()

        val cu: CameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)
        mMap.animateCamera(cu)

        Toast.makeText(applicationContext,"Clicked request title is ${incomingRequestObject.requestTitle}", Toast.LENGTH_LONG ).show()
        // Add a marker for the driver and move the camera
//        val driverMarker = driverLocation
//        mMap.addMarker(MarkerOptions().position(driverMarker).title("Driver Location"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(driverMarker))
    }
}
