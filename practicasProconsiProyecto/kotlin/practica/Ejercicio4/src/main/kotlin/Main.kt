import java.sql.DriverManager
import java.time.LocalDate

fun main() {

    DriverManager.getConnection("jdbc:postgresql://localhost:5432/proconsi_bd", "postgres", "pssw1").use { conexion ->
        println("Conexión exitosa: $conexion")

        val dao = ClienteDAO(conexion)


        val cliente = Cliente(
            dni = "70834040V",
            nombre = "Roberto",
            apellidos = "García",
            tipo_cliente = "SOCIO",
            cuota_maxima = 299.0,
            fecha_alta = LocalDate.now()
        )

        //dao.crear(cliente)

        //dao.eliminarCliente("70834038V")

        //dao.consultarCliente ("70834037V")

        //orderBy fecha:
        dao.listarClientes(LocalDate.now().toString())

        dao.listarClientes("dni")



    }

}
