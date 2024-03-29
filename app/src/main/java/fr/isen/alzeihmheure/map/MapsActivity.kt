package fr.isen.alzeihmheure.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.FirebaseDatabase
import fr.isen.alzeihmheure.R


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var myHandler: Handler
    private lateinit var mMap: GoogleMap

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@MapsActivity)
        fetchLocation()
        myHandler = Handler(mainLooper)
        myHandler.postDelayed(myRunnable, 500)
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                Toast.makeText(applicationContext, currentLocation.latitude.toString() + "" +
                        currentLocation.longitude, Toast.LENGTH_SHORT).show()

                val database = FirebaseDatabase.getInstance()
                val myRefLat = database.getReference("users/user1/coordonnées/latitude")
                val myRefLon = database.getReference("users/user1/coordonnées/longitude")
                myRefLat.child("latitude").setValue(currentLocation.latitude)
                myRefLon.child("longitude").setValue(currentLocation.longitude)

                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map) as
                        SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this@MapsActivity)
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        val monMarker = googleMap.addMarker(MarkerOptions().position(latLng).title("je suis ou?"))
       monMarker.setPosition(latLng);
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
                                            grantResults: IntArray) {
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }
    private val myRunnable: Runnable = object : Runnable {
        override fun run() {
            // Code à éxécuter de façon périodique
            myHandler.postDelayed(this, 500)
        }
    }
    override fun onPause() {
        super.onPause()
        myHandler.removeCallbacks(myRunnable) // On arrete le callback
    }
}