package edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.RegistroEntradaHuacales

data class RegistroEntradaHuacalesState(
    val idEntrada: Int? = null,
    val nombreCliente: String = "",
    val fecha: String = "",
    val cantidad: Int = 0,
    val precio: Double = 0.0
)