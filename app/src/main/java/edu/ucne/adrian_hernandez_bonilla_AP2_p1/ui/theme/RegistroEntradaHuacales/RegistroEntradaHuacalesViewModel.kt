package edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.RegistroEntradaHuacales

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Model.EntradaHuacales
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Repository.EntradaHuacalesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroEntradaHuacalesViewModel @Inject constructor(
    private val repository: EntradaHuacalesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RegistroEntradaHuacalesState())
    val state: StateFlow<RegistroEntradaHuacalesState> = _state.asStateFlow()

    val entradas: StateFlow<List<EntradaHuacales>> = repository.getAllEntradas().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _event = Channel<String>()
    val event = _event.receiveAsFlow()

    private fun sendEvent(message: String) {
        viewModelScope.launch {
            _event.send(message)
        }
    }

    fun onEvent(event: RegistroEntradaHuacalesEvent) {
        when (event) {
            is RegistroEntradaHuacalesEvent.NombreChanged -> {
                _state.value = _state.value.copy(nombreCliente = event.value)
            }
            is RegistroEntradaHuacalesEvent.FechaChanged -> {
                _state.value = _state.value.copy(fecha = event.value)
            }
            is RegistroEntradaHuacalesEvent.CantidadChanged -> {
                _state.value = _state.value.copy(cantidad = event.value)
            }
            is RegistroEntradaHuacalesEvent.PrecioChanged -> {
                _state.value = _state.value.copy(precio = event.value)
            }
            is RegistroEntradaHuacalesEvent.Save -> {
                insertarEntrada(event.entrada)
            }
            is RegistroEntradaHuacalesEvent.Delete -> {
                eliminarEntrada(event.entrada)
            }
            is RegistroEntradaHuacalesEvent.Select -> {
                _state.value = _state.value.copy(
                    idEntrada = event.entrada.idEntrada,
                    nombreCliente = event.entrada.nombreCliente,
                    fecha = event.entrada.fecha,
                    cantidad = event.entrada.cantidad,
                    precio = event.entrada.precio
                )
            }
        }
    }

    private fun insertarEntrada(entrada: EntradaHuacales) {
        viewModelScope.launch {
            val lista = entradas.value
            if (lista.any { it.nombreCliente.equals(entrada.nombreCliente, ignoreCase = true) }) {
                sendEvent("No se puede agregar una entrada con el mismo nombre de cliente")
            } else {
                // Si idEntrada es null o 0 => se inserta como null para que Room autogenere
                val entradaAInsertar = if (entrada.idEntrada == null || entrada.idEntrada == 0) {
                    entrada.copy(idEntrada = null)
                } else entrada

                repository.insertEntrada(entradaAInsertar)
            }
        }
    }

    private fun eliminarEntrada(entrada: EntradaHuacales) {
        viewModelScope.launch {
            repository.deleteEntrada(entrada)
        }
    }
}
