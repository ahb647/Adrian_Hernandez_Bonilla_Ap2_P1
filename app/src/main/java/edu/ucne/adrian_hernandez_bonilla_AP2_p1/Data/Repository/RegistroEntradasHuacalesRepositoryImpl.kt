package edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Repository

import asExternalModel
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Local.Dao.EntradaHuacalesDao
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Model.EntradaHuacales
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Repository.EntradaHuacalesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import toEntity
import javax.inject.Inject

class EntradaHuacalesRepositoryImpl @Inject constructor(
    private val dao: EntradaHuacalesDao
) : EntradaHuacalesRepository {

    override fun getAllEntradas(): Flow<List<EntradaHuacales>> =
        dao.observedAll().map { list -> list.map { it.asExternalModel() } }

    override suspend fun getEntradaById(id: Int): EntradaHuacales? =
        dao.getById(id)?.asExternalModel()

    override suspend fun insertEntrada(entrada: EntradaHuacales) {
        // Forzar id = 0 para autogenerar
        dao.insert(entrada.toEntity().copy(idEntrada = 0))
    }

    override suspend fun updateEntrada(entrada: EntradaHuacales) {
        dao.update(entrada.toEntity())
    }

    override suspend fun deleteEntrada(entrada: EntradaHuacales) =
        dao.delete(entrada.toEntity())

    override suspend fun deleteEntradaById(id: Int) =
        dao.deleteById(id)
}