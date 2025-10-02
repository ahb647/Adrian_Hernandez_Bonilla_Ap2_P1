package edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.RegistroEntradaHuacales



import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Model.EntradaHuacales

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroEntradaHuacalesScreen(
    state: RegistroEntradaHuacalesState,
    onEvent: (RegistroEntradaHuacalesEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Registro de Huacales") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "navigate back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.nombreCliente,
                onValueChange = { onEvent(RegistroEntradaHuacalesEvent.NombreChanged(it)) },
                label = { Text("Nombre del cliente") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.fecha,
                onValueChange = { /* No permitir cambiar, siempre hoy */ },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )

            OutlinedTextField(
                value = state.cantidad.toString(),
                onValueChange = { onEvent(RegistroEntradaHuacalesEvent.CantidadChanged(it.toIntOrNull() ?: 0)) },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.precio.toString(),
                onValueChange = { onEvent(RegistroEntradaHuacalesEvent.PrecioChanged(it.toDoubleOrNull() ?: 0.0)) },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    onEvent(
                        RegistroEntradaHuacalesEvent.Save(
                            entrada = EntradaHuacales(
                                idEntrada = state.idEntrada,
                                nombreCliente = state.nombreCliente,
                                fecha = state.fecha,
                                cantidad = state.cantidad,
                                precio = state.precio
                            )
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(text = "Guardar")
            }
        }
    }
}