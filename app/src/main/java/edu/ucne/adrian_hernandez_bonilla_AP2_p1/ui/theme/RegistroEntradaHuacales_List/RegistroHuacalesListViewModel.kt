package edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.RegistroEntradaHuacales_List

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Model.EntradaHuacales
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Repository.EntradaHuacalesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroEntradaHuacalesListViewModel @Inject constructor(
    private val repository: EntradaHuacalesRepository
) : ViewModel() {

    private val _entradas = MutableStateFlow<List<EntradaHuacales>>(emptyList())
    val entradas: StateFlow<List<EntradaHuacales>> = _entradas.asStateFlow()

    private val _entradaSeleccionada = MutableStateFlow<EntradaHuacales?>(null)
    val entradaSeleccionada: StateFlow<EntradaHuacales?> = _entradaSeleccionada.asStateFlow()

    init {
        obtenerTodasEntradas()
    }

    private fun obtenerTodasEntradas() {
        viewModelScope.launch {
            repository.getAllEntradas().collectLatest { lista ->
                _entradas.value = lista
            }
        }
    }

    fun obtenerEntradaPorId(id: Int) {
        viewModelScope.launch {
            _entradaSeleccionada.value = repository.getEntradaById(id)
        }
    }

    fun insertarEntrada(entrada: EntradaHuacales) {
        viewModelScope.launch {
            val existe = _entradas.value.any { it.nombreCliente.equals(entrada.nombreCliente, ignoreCase = true) }
            if (existe) {
                Log.d("RegistroEntradaHuacalesVM", "No se puede agregar una entrada con el mismo nombre de cliente: ${entrada.nombreCliente}")
            } else {
                repository.insertEntrada(entrada)
            }
        }
    }

    fun actualizarEntrada(entrada: EntradaHuacales) {
        viewModelScope.launch {
            repository.updateEntrada(entrada)
        }
    }

    fun eliminarEntrada(entrada: EntradaHuacales) {
        viewModelScope.launch {
            repository.deleteEntrada(entrada)
        }
    }
}
