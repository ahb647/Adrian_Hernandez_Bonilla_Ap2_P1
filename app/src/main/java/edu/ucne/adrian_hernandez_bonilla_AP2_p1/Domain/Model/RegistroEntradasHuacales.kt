package edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Model

data class EntradaHuacales(
    val idEntrada: Int? = null,
    val fecha: String = "",
    val nombreCliente: String = "",
    val cantidad: Int = 0,
    val precio: Double = 0.0
)