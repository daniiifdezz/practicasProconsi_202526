import java.time.LocalDate

data class Cliente(
    val id: Long? = null,
    val dni: String,
    val nombre: String,
    val apellidos: String,
    val tipo_cliente: String,
    val cuota_maxima: Double?,
    val fecha_alta: LocalDate
)