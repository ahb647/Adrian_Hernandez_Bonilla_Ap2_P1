import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Data.Local.Entity.EntradaHuacalesEntity
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Model.EntradaHuacales

// Convierte la entidad de Room a modelo de dominio
fun EntradaHuacalesEntity.asExternalModel(): EntradaHuacales {
    return EntradaHuacales(
        idEntrada = this.idEntrada,
        fecha = this.fecha,
        nombreCliente = this.nombreCliente,
        cantidad = this.cantidad,
        precio = this.precio
    )
}


fun EntradaHuacales.toEntity(): EntradaHuacalesEntity {
    return EntradaHuacalesEntity(
        idEntrada = this.idEntrada,
        fecha = this.fecha,
        nombreCliente = this.nombreCliente,
        cantidad = this.cantidad,
        precio = this.precio
    )
}