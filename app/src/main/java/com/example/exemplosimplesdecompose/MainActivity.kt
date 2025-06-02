package com.example.exemplosimplesdecompose

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exemplosimplesdecompose.ui.theme.ExemploSimplesDeComposeTheme
import com.example.exemplosimplesdecompose.view.AlcoolGasolinaPreco
import com.example.exemplosimplesdecompose.view.ListofGasStations
import com.example.exemplosimplesdecompose.view.Welcome
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private lateinit var fusedLocationClient: FusedLocationProviderClient
class MainActivity : ComponentActivity() {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var check= false
        var latitude =""
        var longitude =""

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                     latitude  = it.latitude.toString()
                     longitude = it.longitude.toString()
                    // Faça algo com latitude e longitude
                }
            }


        check=loadConfig(this)
        setContent {
            ExemploSimplesDeComposeTheme {
                val navController: NavHostController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") { Welcome(navController) }
                   // composable("input") { InputView(navController) }
                    composable("mainalcgas") { AlcoolGasolinaPreco(navController,check, latitude, longitude) }
                    composable("listaDePostos/{posto}") { backStackEntry ->
                        val posto = backStackEntry.arguments?.getString("posto") ?: ""
                        ListofGasStations(navController, posto, latitude, longitude)
                    }

                }
            }
        }
    }


    fun loadConfig(context: Context):Boolean{
        val sharedFileName="config_Alc_ou_Gas"
        var sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
        var is_75_checked=false
        if(sp!=null) {
            is_75_checked = sp.getBoolean("is_75_checked", false)
        }
        return is_75_checked
    }
}

