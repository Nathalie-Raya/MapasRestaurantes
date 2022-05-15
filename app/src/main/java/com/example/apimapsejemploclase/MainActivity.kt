package com.example.apimapsejemploclase

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.apimapsejemploclase.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
lateinit var context:Context
    private lateinit var binding: ActivityMainBinding
    private lateinit var map:GoogleMap
    companion object{
        const val REQUEST_CODE_LOCATION=0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivCabo.setOnClickListener{
            val coordenadas = LatLng(20.21278688761251,-100.88276253903847)
            val marker = MarkerOptions().position(coordenadas).title("Cabo 66")
            map.addMarker(marker)
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(coordenadas,18f),
                4000,null
            )
        }
        binding.ivQuijote.setOnClickListener{
            val coordenadas = LatLng(20.21298740827155,-100.88276877428765)
            val marker = MarkerOptions().position(coordenadas).title("Cafe El Quijote")
            map.addMarker(marker)
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(coordenadas,18f),
                4000,null
            )
        }
        binding.ivVeranda.setOnClickListener{

            val coordenadas = LatLng(20.21398268002223,-100.88205837893334)
            val marker = MarkerOptions().position(coordenadas).title("La Veranda")
            map.addMarker(marker)
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(coordenadas,18f),
                4000,null
            )

        }
        createFragment()
    }

    private fun enableLocation(){
        if(!::map.isInitialized)return
        if(isLocationPermissionGranted())
        {
            map.isMyLocationEnabled=true
        }else{
            requestLocationPermission()
        }
    }
    private fun  createFragment(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
       crearMarker()
        enableLocation()
    }
    private fun crearMarker(){
        val coordenadas = LatLng(19.302516, -99.150592)
        val marker = MarkerOptions().position(coordenadas).title("Este es el estadio azteca")
        map.addMarker(marker)
    }
    private fun crearMarkerRestaurant(latitud: Double,longitud: Double,descripcion: String){
        val coordenadas = LatLng(latitud,longitud)
        val marker = MarkerOptions().position(coordenadas).title(descripcion)
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas,18f),
            4000,null
        )
    }


    private fun isLocationPermissionGranted()=
        ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            //Mostrar la ventan de permiso
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            REQUEST_CODE_LOCATION->if (grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled=true
            }
            else{
                Toast.makeText(this,"Activa tus servicios manualmente",Toast.LENGTH_LONG).show()
            }
            else->{}

        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }




}