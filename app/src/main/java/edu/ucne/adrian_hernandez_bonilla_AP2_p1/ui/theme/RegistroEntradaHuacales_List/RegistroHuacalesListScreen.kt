package edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.RegistroEntradaHuacales_List

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Model.EntradaHuacales
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroEntradaHuacalesListScreen(
    viewModel: RegistroEntradaHuacalesListViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val entradas by viewModel.entradas.collectAsState()
    var idEntrada by remember { mutableStateOf<Int?>(null) }
    var nombreCliente by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    val fechaHoy = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registro de Huacales") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.Inventory2, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Entradas Registradas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Nombre del cliente
            OutlinedTextField(
                value = nombreCliente,
                onValueChange = { nombreCliente = it },
                label = { Text("Nombre del cliente") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Cantidad
            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it.filter { char -> char.isDigit() } },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Precio
            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it.filter { char -> char.isDigit() || char == '.' } },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Botones lado a lado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        if (nombreCliente.isBlank()) return@Button

                        val entrada = EntradaHuacales(
                            idEntrada = idEntrada ?: 0,
                            nombreCliente = nombreCliente,
                            fecha = fechaHoy,
                            cantidad = cantidad.toIntOrNull() ?: 0,
                            precio = precio.toDoubleOrNull() ?: 0.0
                        )

                        if (idEntrada == null) viewModel.insertarEntrada(entrada)
                        else viewModel.actualizarEntrada(entrada)

                        idEntrada = null
                        nombreCliente = ""
                        cantidad = ""
                        precio = ""
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Guardar")
                }

                Button(
                    onClick = {
                        if (idEntrada != null) {
                            val entrada = EntradaHuacales(
                                idEntrada = idEntrada ?: 0,
                                nombreCliente = nombreCliente,
                                fecha = fechaHoy,
                                cantidad = cantidad.toIntOrNull() ?: 0,
                                precio = precio.toDoubleOrNull() ?: 0.0
                            )
                            viewModel.actualizarEntrada(entrada)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Actualizar")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (entradas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay entradas registradas")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(entradas) { entrada ->
                        EntradaHuacalesItem(
                            entrada = entrada,
                            onDelete = { viewModel.eliminarEntrada(entrada) },
                            onSelect = {
                                idEntrada = entrada.idEntrada
                                nombreCliente = entrada.nombreCliente
                                cantidad = entrada.cantidad.toString()
                                precio = entrada.precio.toString()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EntradaHuacalesItem(
    entrada: EntradaHuacales,
    onDelete: () -> Unit,
    onSelect: () -> Unit
) {
    Card(
        onClick = onSelect,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Inventory2,
                    contentDescription = "Entrada",
                    modifier = Modifier.padding(end = 16.dp)
                )
                Column {
                    Text(
                        text = entrada.nombreCliente,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Fecha: ${entrada.fecha}, Cantidad: ${entrada.cantidad}, Precio: ${entrada.precio}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}