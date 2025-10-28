import java.sql.DriverManager
import java.time.LocalDate
import java.time.LocalDateTime

fun main() {

    DriverManager.getConnection("jdbc:postgresql://localhost:5432/proconsi_bd", "postgres", "pssw1").use { conexion ->
        println("Conexión exitosa: $conexion")

        val dao = ClienteDAO(conexion)

        while (true) {
            println(
                """
           
            1. Añadir cliente
            2. Consultar cliente
            3. Editar cliente
            4. Eliminar cliente
            5. Listar clientes
            6. Salir 
       
            
            Elige una opción:
            """.trimIndent()
            )
            val opcion = readln().toIntOrNull()
            when (opcion) {
                1 -> {
                    println("DNI: ")
                    val dni = readln()
                    println("Nombre: ")
                    val nombre = readln()
                    println("Apellidos: ")
                    val apellidos = readln()
                    print("Tipo de cliente (REGISTRADO/SOCIO): ")
                    val tipo = readln().uppercase()

                    val cuota: Double? = if (tipo == "REGISTRADO") {
                        50.0
                    } else {
                        null
                    }

                    val cliente = Cliente(
                        dni = dni,
                        nombre = nombre,
                        apellidos = apellidos,
                        tipo_cliente = tipo,
                        cuota_maxima = cuota,
                        fecha_alta = LocalDate.now()
                    )
                    dao.crear(cliente)
                    println("Cliente creado correctamente.")
                }

                2 -> {
                    println("DNI: ")
                    val dni = readln()
                    val cliente = dao.consultarCliente(dni)


                }

                3 -> {
                    println("DNI: ")
                    val dni = readln()
                    val clienteExistente = dao.consultarCliente(dni)

                    if (clienteExistente == null) {
                        println("El cliente no esta en la base de datos.")
                        continue

                    } else {
                        println("Nombre: ")
                        val nombre = readln()
                        println("Apellidos: ")
                        val apellidos = readln()
                        println("Tipo (REGISTRADO/SOCIO): ")
                        val tipo = readln().uppercase()
                        val cuota = if (tipo == "REGISTRADO") {
                            50.0
                        } else {
                            null
                        }
                        val updateCliente = Cliente(
                            dni = dni,
                            nombre = nombre,
                            apellidos = apellidos,
                            tipo_cliente = tipo,
                            cuota_maxima = cuota,
                            fecha_alta = LocalDate.now()
                        )
                        dao.editarCliente(updateCliente)
                        println("Cliente actualizado correctamente.")

                    }


                }

                4 -> {
                    println("Introduce el dni del cliente a borrar: ")
                    val dni = readln()
                    dao.eliminarCliente(dni)
                    println("Cliente eliminado correctamente.")
                }

                5 -> {
                    println("Ordenar por:")
                    println("1. DNI")
                    println("2. Fecha de alta")
                    when (readln().toIntOrNull()) {
                        1 -> {
                            dao.listarClientes("dni")
                        }

                        2 -> {
                            dao.listarClientes("fecha_alta")
                        }

                        else -> dao.listarClientes("dni")
                    }

                }

                6 -> {
                    conexion.close()
                    break
                }

                else ->
                    println("Selecciona una opción válida.")

            }
        }


    }

}
