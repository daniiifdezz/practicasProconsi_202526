import java.sql.Connection

class ClienteDAO(val conexion: Connection){


    fun crear (cliente: Cliente){
        val sql = """
            INSERT INTO clientes (dni, nombre, apellidos, tipo_cliente, cuota_maxima, fecha_alta)
            VALUES (?, ?, ?, ?, ?,?)
        """.trimIndent()
        //trimIndent para no tener espacios innecesarios

        conexion.prepareStatement(sql).use { ps ->
            ps.setString(1, cliente.dni)
            ps.setString(2, cliente.nombre)
            ps.setString(3, cliente.apellidos)
            ps.setString(4, cliente.tipo_cliente)

            //si el cliente es registrado le metemos la cuota maxima
            if (cliente.tipo_cliente == "REGISTRADO") {
                ps.setDouble(5, cliente.cuota_maxima ?: 0.0)
            } else {
                ps.setNull(5, java.sql.Types.DOUBLE)
            }

            ps.setDate(6, java.sql.Date.valueOf(cliente.fecha_alta))

            ps.executeUpdate()
        }

    }


}