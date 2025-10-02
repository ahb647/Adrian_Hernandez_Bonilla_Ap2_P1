package edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.RegistroEntradaHuacales

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Model.EntradaHuacales
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroEntradaHuacalesListOnlyScreen(
    entradas: List<EntradaHuacales>,
    onDelete: (EntradaHuacales) -> Unit,
    onSaveEdit: (EntradaHuacales) -> Unit,
    onNavigateBack: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var entradaToDelete by remember { mutableStateOf<EntradaHuacales?>(null) }

    var entradaToEdit by remember { mutableStateOf<EntradaHuacales?>(null) }
    var editNombre by remember { mutableStateOf("") }
    var editCantidad by remember { mutableStateOf("") }
    var editPrecio by remember { mutableStateOf("") }

    var showErrorEmptyName by remember { mutableStateOf(false) }
    var showErrorEmptyCantidad by remember { mutableStateOf(false) }
    var showErrorInvalidChars by remember { mutableStateOf(false) }
    var showErrorDuplicateName by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val filteredEntradas = remember(entradas, searchText) {
        entradas.filter {
            it.nombreCliente.contains(searchText, ignoreCase = true) ||
                    it.idEntrada.toString() == searchText
        }
    }

    val totalHuacales = entradas.size
    val totalPrecio = entradas.sumOf { it.precio }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Huacales Registrados") }
                // Botón de flecha eliminado
            )
        },
        bottomBar = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Huacales: $totalHuacales",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1
                    )
                    Text(
                        "Total: $${"%.2f".format(totalPrecio)}",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // Barra de búsqueda
            item {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Buscar por nombre o id") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Botón volver al formulario
            item {
                Button(
                    onClick = onNavigateBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver al Formulario")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Mensaje si no hay entradas
            if (filteredEntradas.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay entradas registradas")
                    }
                }
            } else {
                items(filteredEntradas) { entrada ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "Cliente: ${entrada.nombreCliente}",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(4.dp))
                                Text("ID: ${entrada.idEntrada}", style = MaterialTheme.typography.bodyMedium)
                                Text("Fecha: ${entrada.fecha}", style = MaterialTheme.typography.bodyMedium)
                                Text("Cantidad: ${entrada.cantidad}", style = MaterialTheme.typography.bodyMedium)
                                Text("Precio: ${entrada.precio}", style = MaterialTheme.typography.bodyMedium)
                            }

                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        entradaToEdit = entrada
                                        editNombre = entrada.nombreCliente
                                        editCantidad = entrada.cantidad.toString()
                                        editPrecio = entrada.precio.toString()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text("Editar")
                                }

                                Button(
                                    onClick = {
                                        entradaToDelete = entrada
                                        showDeleteDialog = true
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo de eliminación
    if (showDeleteDialog && entradaToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de eliminar a ${entradaToDelete?.nombreCliente}?") },
            confirmButton = {
                Button(onClick = {
                    entradaToDelete?.let { onDelete(it) }
                    showDeleteDialog = false
                }) { Text("Sí") }
            },
            dismissButton = { Button(onClick = { showDeleteDialog = false }) { Text("No") } }
        )
    }

    // Diálogo de edición con validaciones
    if (entradaToEdit != null) {
        AlertDialog(
            onDismissRequest = { entradaToEdit = null },
            title = { Text("Editar Huacal") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editNombre,
                        onValueChange = { editNombre = it },
                        label = { Text("Nombre del cliente") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editCantidad,
                        onValueChange = { editCantidad = it.filter { c -> c.isDigit() } },
                        label = { Text("Cantidad") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editPrecio,
                        onValueChange = { editPrecio = it.filter { c -> c.isDigit() || c == '.' } },
                        label = { Text("Precio") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (showErrorEmptyName) Text("Debe ingresar un nombre", color = Color.Red)
                    if (showErrorEmptyCantidad) Text("Debe ingresar una cantidad", color = Color.Red)
                    if (showErrorInvalidChars) Text("El nombre no puede contener caracteres especiales", color = Color.Red)
                    if (showErrorDuplicateName) Text("Ya existe un huacal con ese nombre", color = Color.Red)
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (editNombre.isBlank()) {
                        showErrorEmptyName = true
                        coroutineScope.launch { kotlinx.coroutines.delay(2000); showErrorEmptyName = false }
                        return@Button
                    }
                    if (editCantidad.isBlank() || editCantidad.toIntOrNull() == null) {
                        showErrorEmptyCantidad = true
                        coroutineScope.launch { kotlinx.coroutines.delay(2000); showErrorEmptyCantidad = false }
                        return@Button
                    }
                    val regex = Regex("^[a-zA-Z0-9 ]+$")
                    if (!regex.matches(editNombre)) {
                        showErrorInvalidChars = true
                        coroutineScope.launch { kotlinx.coroutines.delay(2000); showErrorInvalidChars = false }
                        return@Button
                    }
                    if (entradas.any { it.nombreCliente.equals(editNombre, ignoreCase = true) && it.idEntrada != entradaToEdit?.idEntrada }) {
                        showErrorDuplicateName = true
                        coroutineScope.launch { kotlinx.coroutines.delay(2000); showErrorDuplicateName = false }
                        return@Button
                    }

                    entradaToEdit?.let {
                        val updated = it.copy(
                            nombreCliente = editNombre,
                            cantidad = editCantidad.toIntOrNull() ?: it.cantidad,
                            precio = editPrecio.toDoubleOrNull() ?: it.precio
                        )
                        onSaveEdit(updated)
                    }
                    entradaToEdit = null
                }) { Text("Guardar") }
            },
            dismissButton = { Button(onClick = { entradaToEdit = null }) { Text("Cancelar") } }
        )
    }
}
