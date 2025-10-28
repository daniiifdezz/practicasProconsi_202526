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
            if (cliente.cuota_maxima != null) {
                ps.setDouble(5, cliente.cuota_maxima)
            } else {
                ps.setNull(5, java.sql.Types.DOUBLE)
            }

            ps.setDate(6, Date.valueOf(cliente.fecha_alta))

            ps.executeUpdate()
        }

    }

    fun eliminarCliente (dni: String): Int{
        val sql = """DELETE FROM clientes WHERE dni = ?"""
        conexion.prepareStatement(sql).use { ps ->
            ps.setString(1, dni)
            return ps.executeUpdate()
        }
    }

    fun consultarCliente(dni: String ): Cliente?{
        val sql = """SELECT * FROM clientes WHERE dni = ?"""

        conexion.prepareStatement(sql).use { ps ->
            ps.setString(1, dni)
            val resultSet = ps.executeQuery()


            //puntero que va fila a fila, se mueve a la siguiente fila con .next
            //si hay fila devuelve true si no false
            return if (resultSet.next()) {
                val cuota = resultSet.getDouble("cuota_maxima")
                val cuotaOrNull = if (resultSet.wasNull()) null else cuota
                 Cliente(
                    id = resultSet.getLong("id"),
                    dni = resultSet.getString("dni"),
                    nombre = resultSet.getString("nombre"),
                    apellidos = resultSet.getString("apellidos"),
                    tipo_cliente = resultSet.getString("tipo_cliente"),
                    cuota_maxima = cuotaOrNull,
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

                val cuota = resultSet.getDouble("cuota_maxima")
                val cuotaOrNull = if (resultSet.wasNull()) null else cuota

                val cuotaTexto = cuotaOrNull?.let { ", cuota=${"%.2f".format(it).replace('.', ',')}" } ?: ""


                    val id = resultSet.getLong("id")
                    val dni = resultSet.getString("dni")
                    val nombre = resultSet.getString("nombre")
                    val apellidos = resultSet.getString("apellidos")
                    val tipo = resultSet.getString("tipo_cliente")
                    val fecha = resultSet.getDate("fecha_alta").toLocalDate()

                    println("id=$id, dni=$dni, nombre=$nombre, apellidos=$apellidos, tipo=$tipo$cuotaTexto, fecha=$fecha")

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