package edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Local.Dao.EntradaHuacalesDao
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Local.Entity.EntradaHuacalesEntity

@Database(
    entities = [EntradaHuacalesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RegistroEntradasHuacalesDatabase : RoomDatabase() {
    abstract fun entradaHuacalesDao(): EntradaHuacalesDao
}