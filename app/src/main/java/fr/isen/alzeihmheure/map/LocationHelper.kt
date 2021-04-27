package fr.isen.alzeihmheure.map

class LocationHelper {


    private var Longitude = 0.0
    private var Latitude = 0.0

    fun LocationHelper(longitude: Double, latitude: Double) {
        Longitude = longitude
        Latitude = latitude
    }

    fun getLongitude(): Double {
        return Longitude
    }

    fun setLongitude(longitude: Double) {
        Longitude = longitude
    }

    fun getLatitude(): Double {
        return Latitude
    }

    fun setLatitude(latitude: Double) {
        Latitude = latitude
    }
}