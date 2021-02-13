package com.quakes.quakemap

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.quakes.R
import com.quakes.model.Earthquake
import com.quakes.quakeslist.QuakesListFragment.Companion.QUAKE_DETAILS

class MapsMarkerActivity : AppCompatActivity(), OnMapReadyCallback {

    private var quakeDetails: Earthquake? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        if (getString(R.string.maps_api_key).isEmpty()) {
            Toast.makeText(this, "Add your own API key in app/secure.properties as MAPS_API_KEY=YOUR_API_KEY", Toast.LENGTH_LONG).show()
        }

        quakeDetails = intent.getParcelableExtra(QUAKE_DETAILS)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        quakeDetails?.let { quake ->
            if(quake.lat == null || quake.lng == null) {
                Toast.makeText(this, "Invalid coordinates", Toast.LENGTH_LONG)
            } else {
                googleMap?.apply {
                    val position = LatLng(quake.lat, quake.lng)
                    addMarker(
                        MarkerOptions()
                            .position(position)
                            .title(quake.eqid)
                    )
                    moveCamera(CameraUpdateFactory.newLatLng(position))
                }
            }
        }
    }
}