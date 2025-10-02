package edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Local.Entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EntradasHuacales")
data class EntradaHuacalesEntity(
    @PrimaryKey(autoGenerate = true)
    val idEntrada: Int? = 0, // NO nullable
    val fecha: String = "",
    val nombreCliente: String = "",
    val cantidad: Int = 0,
    val precio: Double = 0.0
)