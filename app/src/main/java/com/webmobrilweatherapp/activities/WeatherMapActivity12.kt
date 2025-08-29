package com.webmobrilweatherapp.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.webmobrilweatherapp.R


class WeatherMapActivity : FragmentActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var weatherTileProvider: WeatherTileProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_map12)

        // Initialize the map fragment and set the callback
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        // Initialize the weather tile provider
        weatherTileProvider = WeatherTileProvider("wkw7ho4Gya6HakuE7dNcEVEHIVJMZAhU")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
       // mMap.setMyLocationEnabled(true);
        val indiaLocation = LatLng(20.5937, 78.9629) // Center over India
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(indiaLocation, 5f))

        // Add a default weather layer (rain) overlay
        addWeatherLayer("precipitation_new")

        // You can switch between different layers: snow, clouds, temperature, etc.
    }

    private fun addWeatherLayer(layerType: String) {
        val tileOverlayOptions = weatherTileProvider?.getTileProvider(layerType)?.let {
            TileOverlayOptions()
                .tileProvider(it)
        }
        val tileOverlay = tileOverlayOptions?.let { mMap!!.addTileOverlay(it) }
    }
}
