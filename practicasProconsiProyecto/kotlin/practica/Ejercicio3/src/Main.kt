import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.util.Date

/**Introducción de 2 fechas con el siguiente formato yyyy/MM/dd me calcule:
-	Diferencia de días entre las 2 fechas
-	Inicio y fin de año para cada una de las 2 fechas
-	Número de días del año de cada una de las 2 fechas
-	Número de la semana de cada una de las 2 fechas
*/



fun main() {

    println ("Introduzca la primera fecha con el siguiente formato: yyyy/MM/dd")

    val entrada1:String = readLine()!!

    println ("Introduzca la segunda fecha: yyyy/MM/dd")

    val entrada2:String= readLine()!!


    diferenciaFechas(entrada1,entrada2)

    principioFinFechas(entrada1,entrada2)


}


fun diferenciaFechas(entrada1: String, entrada2: String) {

    val formato = DateTimeFormatter.ofPattern("yyyy/MM/dd")


    if (!entrada1.isNullOrBlank()&& !entrada2.isNullOrBlank()) {
        try{
            //parseamos la entrada al formato indicado de la fecha
            val fecha1 = LocalDate.parse(entrada1, formato)
            val fecha2 = LocalDate.parse (entrada2, formato)

            //funcion de kotlin
            val diferenciaDias = ChronoUnit.DAYS.between(fecha1, fecha2)

            if (fecha1!=fecha2){
                //println("Las fechas introducidas son diferentes.")
                println("La diferencia de días entre ambas fechas es de: $diferenciaDias")


            }else{
                println("Usted está en el mismo día, no hay diferencia de días.")
            }

        }catch (e: DateTimeParseException){
            println("El formato de alguna de las fechas es incorrecto.")
        }
    }
    else{
        println("Debe introducir la fechas por la terminal.")
    }

}


fun principioFinFechas (entrada1: String, entrada2: String) {

    val formato = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    if (!entrada1.isNullOrBlank()&& !entrada2.isNullOrBlank()) {
        try{
            val fecha1 = LocalDate.parse(entrada1, formato)
            val fecha2 = LocalDate.parse (entrada2, formato)



            val principioAño1 = LocalDate.of(fecha1.year,1,1)
            val principioAño2 = LocalDate.of(fecha2.year,1,1)
            val finalAño1= LocalDate.of(fecha1.year,12,31)
            val finalAño2 = LocalDate.of(fecha2.year,12,31)

            if (fecha1!=fecha2)
                //println("Las fechas introducidas son diferentes.")
                println("Inicio y fin de año para la primera fecha es de: $principioAño1 y de $finalAño1")
                println("Inicio y fin de año para la segunda fecha es de: $principioAño2 y de $finalAño2")

        }catch (e: DateTimeParseException){
            println("El formato de alguna de las fechas es incorrecto.")
        }
    }
    else{
        println("Debe introducir la fechas por la terminal.")
    }


}

