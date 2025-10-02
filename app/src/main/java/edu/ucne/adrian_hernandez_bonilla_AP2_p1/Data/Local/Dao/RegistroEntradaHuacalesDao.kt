package edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Local.Dao

import androidx.room.*
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Local.Entity.EntradaHuacalesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntradaHuacalesDao {



    @Query("SELECT * FROM EntradasHuacales ORDER BY idEntrada DESC")
    fun observedAll(): Flow<List<EntradaHuacalesEntity>>

    @Query("SELECT * FROM EntradasHuacales WHERE idEntrada = :id")
    suspend fun getById(id: Int): EntradaHuacalesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: EntradaHuacalesEntity)

    @Update
    suspend fun update(entity: EntradaHuacalesEntity)

    @Delete
    suspend fun delete(entity: EntradaHuacalesEntity)

    @Query("DELETE FROM EntradasHuacales WHERE idEntrada = :id")
    suspend fun deleteById(id: Int)
}
