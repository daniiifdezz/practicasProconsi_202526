import java.time.LocalDate

data class Cliente(
    val dni: String,
    val nombre: String,
    val apellidos: String,
    val tipo_cliente: String,
    val cuota_maxima: Double,
    val fecha_alta: LocalDate
)