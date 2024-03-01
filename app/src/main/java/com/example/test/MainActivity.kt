package com.example.test

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.test.databinding.ActivityMainBinding
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val PERMISSIONS_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding initialization
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigation setup
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Check and request permissions
        if (checkPermissions()) {
            getWifiInfo()
        } else {
            requestPermissions()
        }
    }

        private fun checkPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_WIFI_STATE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,arrayOf(Manifest.permission.ACCESS_WIFI_STATE),
            PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWifiInfo()
            } else {
                Toast.makeText(
                    this,
                    "Permissão negada para acessar informações de Wi-Fi",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getWifiInfo() {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo? = wifiManager.connectionInfo
        val bssid: String? = wifiInfo?.bssid
        Toast.makeText(
            this,
            "BSSID do Ponto de Acesso Wi-Fi: $bssid",
            Toast.LENGTH_LONG
        ).show()
    }
}
