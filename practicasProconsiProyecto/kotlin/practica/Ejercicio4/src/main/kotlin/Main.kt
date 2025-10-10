import java.sql.DriverManager
import java.time.LocalDate

fun main() {

    DriverManager.getConnection("jdbc:postgresql://localhost:5432/proconsi_bd", "postgres", "pssw1").use { conexion ->
        println("Conexión exitosa: $conexion")

        val dao = ClienteDAO(conexion)

        // Crear un cliente usando los datos que quieras
        val cliente = Cliente(
            dni = "70334322V",
            nombre = "Pedro",
            apellidos = "Martín",
            tipo_cliente = "SOCIO",
            cuota_maxima = 1000.0,
            fecha_alta = LocalDate.now()
        )

        dao.crear(cliente)

        println("Cliente insertado correctamente")
    }

}
