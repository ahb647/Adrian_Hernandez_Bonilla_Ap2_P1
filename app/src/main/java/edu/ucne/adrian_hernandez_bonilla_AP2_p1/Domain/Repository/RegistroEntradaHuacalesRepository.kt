package edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Repository

import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Model.EntradaHuacales
import kotlinx.coroutines.flow.Flow

interface EntradaHuacalesRepository {

    fun getAllEntradas(): Flow<List<EntradaHuacales>>

    suspend fun getEntradaById(id: Int): EntradaHuacales?

    suspend fun insertEntrada(entrada: EntradaHuacales)

    suspend fun updateEntrada(entrada: EntradaHuacales)

    suspend fun deleteEntrada(entrada: EntradaHuacales)

    suspend fun deleteEntradaById(id: Int)
}