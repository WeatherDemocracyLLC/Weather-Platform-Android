package com.webmobrilweatherapp.activities

import com.google.android.gms.maps.model.UrlTileProvider
import java.net.MalformedURLException
import java.net.URL


class WeatherTileProvider(private val apiKey: String) {
    // Returns the appropriate UrlTileProvider for the given weather layer type
    fun getTileProvider(layerType: String?): UrlTileProvider {
        return object : UrlTileProvider(256, 256) {
            override fun getTileUrl(x: Int, y: Int, zoom: Int): URL? {
                // Construct the URL for the weather layer tiles
                val tileUrl = String.format(
                    "%s%s/%d/%d/%d.png?appid=%s",
                    BASE_URL, layerType, zoom, x, y, apiKey
                )

                try {
                    return URL(tileUrl)
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                }
                return null
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://tile.openweathermap.org/map/"
    }
}
