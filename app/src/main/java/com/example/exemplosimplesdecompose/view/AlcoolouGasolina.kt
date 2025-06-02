package com.example.exemplosimplesdecompose.view

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AlcoolGasolinaPreco(
    navController: NavHostController,
    check: Boolean,
    latitude: String,
    longitude: String
) {
    val context = LocalContext.current
    var alcool by remember { mutableStateOf("") }
    var gasolina by remember { mutableStateOf("") }
    var nomeDoPosto by remember { mutableStateOf("") }
    var checkedState by remember { mutableStateOf(check) }
    var lat by remember {mutableStateOf(latitude)}
    var long by remember {mutableStateOf(longitude)}

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo de texto para entrada do preço
            OutlinedTextField(
                value = alcool,
                onValueChange = { alcool = it }, // Atualiza o estado
                label = { Text("Preço do Álcool (R$)") },
                modifier = Modifier.fillMaxWidth(), // Preenche a largura disponível
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Configuração do teclado
            )
            // Campo de texto para preço da Gasolina
            OutlinedTextField(
                value = gasolina,
                onValueChange = { gasolina = it },
                label = { Text("Preço da Gasolina (R$)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            // Campo de texto para preço da Gasolina
            OutlinedTextField(
                value = nomeDoPosto,
                onValueChange = { nomeDoPosto = it },
                label = { Text("Nome do Posto (Opcional))") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "75%",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Switch(
                    modifier = Modifier.semantics { contentDescription = "Escolha o percentual" },
                    checked = checkedState,
                    onCheckedChange = { checkedState = it
                         saveConfig(context,checkedState)
                                      },
                    thumbContent = {
                        if (checkedState) {
                            // Icon isn't focusable, no need for content description
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    }
                )
            }
            // Botão de cálculo
            Button(
                onClick = {

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            // Texto do resultado
            Text(
                text = "Vamos Calcular?",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.End) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("ListaDePostos/$nomeDoPosto")},
                    ) {
                    Icon(Icons.Filled.Add, "Inserir Posto")
                }
            }
        }
    }
}
fun saveConfig(context: Context, switch_state:Boolean){
    val sharedFileName="config_Alc_ou_Gas"
    var sp: SharedPreferences = context.getSharedPreferences(sharedFileName, Context.MODE_PRIVATE)
    var editor = sp.edit()
    editor.putBoolean("is_75_checked",switch_state)
    editor.apply()
}


//// 1. Solicitar permissões
//private val REQUEST_LOCATION_PERMISSION_CODE = 123
//
//// 2. Obter instância do Fused Location Provider Client
//private lateinit var fusedLocationClient: FusedLocationProviderClient
//
//// 3. Solicitar última localização conhecida
//fun getLastLocation() {
//    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
//            if (location != null) {
//                val latitude = location.latitude
//                val longitude = location.longitude
//                // Faça algo com a latitude e longitude (ex: mostrar no mapa, enviar para o servidor)
//            }
//        }
//    } else {
//        // Solicitar permissões
//        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION_CODE)
//    }
//}
//
//// 4. Solicitar atualizações de localização
//private lateinit var locationCallback: LocationCallback
//private val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build()
//
//fun startLocationUpdates() {
//    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(result: LocationResult) {
//                val lastLocation = result.lastLocation
//                val latitude = lastLocation?.latitude
//                val longitude = lastLocation?.longitude
//                // Faça algo com a latitude e longitude (ex: mostrar no mapa, enviar para o servidor)
//            }
//        }
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
//    }
//}
//
//// 5. Desativar atualizações
//fun stopLocationUpdates() {
//    fusedLocationClient.removeLocationUpdates(locationCallback)
//}
//
