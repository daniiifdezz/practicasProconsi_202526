import java.sql.Connection
import java.sql.Date
import java.sql.Timestamp

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

            ps.setDate(6, Date.valueOf(cliente.fecha_alta))

            ps.executeUpdate()
        }

    }

    fun eliminarCliente (dni :String){
        val sql = """DELETE FROM clientes WHERE dni = ?"""
        conexion.prepareStatement(sql).use { ps ->
            ps.setString(1, dni)
            val filaEliminada =  ps.executeUpdate()
            println("Has eliminado a: $filaEliminada")
        }
    }

    fun consultarCliente(dni: String): Cliente?{
        val sql = """SELECT * FROM clientes WHERE dni = ?"""

        conexion.prepareStatement(sql).use { ps ->
            ps.setString(1, dni)
            val resultSet = ps.executeQuery()
            //puntero que va fila a fila, se mueve a la siguiente fila con .next
            //si hay fila devuelve true si no false
            return if (resultSet.next()) {
                 Cliente(
                    dni = resultSet.getString("dni"),
                    nombre = resultSet.getString("nombre"),
                    apellidos = resultSet.getString("apellidos"),
                    tipo_cliente = resultSet.getString("tipo_cliente"),
                    cuota_maxima = resultSet.getDouble ("cuota_maxima"),
                    fecha_alta = resultSet.getDate("fecha_alta").toLocalDate()
                )
            }else{
                null
            }

        }
    }


    //dni  como parametro podria ser por fecha_alta tambien
    fun listarClientes(orderBy: String = "dni"){

        val sql = """SELECT * FROM clientes ORDER BY $orderBy ASC """

        conexion.prepareStatement(sql).use { ps ->

            val resultSet = ps.executeQuery()

            while(resultSet.next()){
                val cliente = Cliente(
                    resultSet.getString("dni"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellidos"),
                    resultSet.getString("tipo_cliente"),
                    resultSet.getDouble("cuota_maxima"),
                    resultSet.getDate("fecha_alta").toLocalDate()
                )
                println(cliente)
            }

        }

    }


      fun editarCliente(cliente: Cliente){

          val sql = """UPDATE clientes SET nombre = ?, apellidos = ?, tipo_cliente =?, cuota_maxima = ?, fecha_alta = ? WHERE dni = ? """.trimIndent()

          //logica para el tipo de cliente
          val cuota_final = if (cliente.tipo_cliente == "REGISTRADO"){
              50.0 //le ponemos esa cantidad como base
          }else{
              cliente.cuota_maxima
          }
              conexion.prepareStatement(sql).use { ps ->
                  ps.setString(1, cliente.nombre)
                  ps.setString(2, cliente.apellidos)
                  ps.setString(3, cliente.tipo_cliente)

                  if (cliente.tipo_cliente == "REGISTRADO") {
                      ps.setDouble(4, 50.0)
                  } else {
                      ps.setNull(4, java.sql.Types.DOUBLE)
                  }

                  ps.setDate(5, java.sql.Date.valueOf(cliente.fecha_alta))
                  ps.setString(6, cliente.dni)

                  ps.executeUpdate()


              }
      }
}