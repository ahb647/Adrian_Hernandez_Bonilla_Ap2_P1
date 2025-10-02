package edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Local.RegistroEntradasHuacalesDatabase
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Local.Dao.EntradaHuacalesDao
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Repository.EntradaHuacalesRepositoryImpl
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Repository.EntradaHuacalesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RegistroEntradasHuacalesDatabase {
        return Room.databaseBuilder(
            context,
            RegistroEntradasHuacalesDatabase::class.java,
            "registro_entradas_huacales_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideEntradaHuacalesDao(db: RegistroEntradasHuacalesDatabase): EntradaHuacalesDao {
        return db.entradaHuacalesDao()
    }

    @Provides
    @Singleton
    fun provideEntradaHuacalesRepository(dao: EntradaHuacalesDao): EntradaHuacalesRepository {
        return EntradaHuacalesRepositoryImpl(dao)
    }
}
