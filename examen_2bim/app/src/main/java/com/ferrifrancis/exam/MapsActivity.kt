package com.ferrifrancis.exam

import Estudiante
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity() {

    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        solicitarPermisos()

        val estudiante = intent.getParcelableExtra<Estudiante>("estudiante")
        if (estudiante != null) {
            val fragmentoMapa =
                supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            fragmentoMapa.getMapAsync { googleMap ->
                if (googleMap != null) {
                    mapa = googleMap
                    establecerConfiguracionMapa()
                    val quicentro = LatLng(estudiante.coordLat!!, estudiante.coordLong!!)
                    val titulo = "Ubicación ${estudiante.nombre}"
                    val zoom = 17f
                    anadirMarcador(quicentro, titulo)
                    moverCamaraConZoom(quicentro, zoom)
                }
            }

        }
    }


    fun anadirMarcador(latLng: LatLng, title: String) {
        mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
    }


    fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f) {
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )
    }

    fun solicitarPermisos() {
        val contexto = this.applicationContext
        //tenemos permisos?
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if (tienePermisos) {
            permisos = true
        } else {//si no tengo permisos, pido permisos
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1 //codigo de peticion de permisos
            )
        }
    }

    fun establecerConfiguracionMapa() {
        val contexto = this.applicationContext
        with(mapa) {
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if (tienePermisos) {
                mapa.isMyLocationEnabled = true //no tenemos aún permisos
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true //no tenemos aun permisos
        }
    }
}