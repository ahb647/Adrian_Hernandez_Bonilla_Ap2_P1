package edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.RegistroEntradaHuacales

import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Model.EntradaHuacales


sealed class RegistroEntradaHuacalesEvent {
    data class NombreChanged(val value: String) : RegistroEntradaHuacalesEvent()
    data class FechaChanged(val value: String) : RegistroEntradaHuacalesEvent()
    data class CantidadChanged(val value: Int) : RegistroEntradaHuacalesEvent()
    data class PrecioChanged(val value: Double) : RegistroEntradaHuacalesEvent()
    data class Save(val entrada: EntradaHuacales) : RegistroEntradaHuacalesEvent()
    data class Delete(val entrada: EntradaHuacales) : RegistroEntradaHuacalesEvent()
    data class Select(val entrada: EntradaHuacales) : RegistroEntradaHuacalesEvent()
}
