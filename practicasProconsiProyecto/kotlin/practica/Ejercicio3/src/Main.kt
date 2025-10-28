import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.time.temporal.WeekFields
import java.util.Date
import java.util.Locale

/**Introducción de 2 fechas con el siguiente formato yyyy/MM/dd me calcule:
-	Diferencia de días entre las 2 fechas
-	Inicio y fin de año para cada una de las 2 fechas
-	Número de días del año de cada una de las 2 fechas -------------REVISAR
-	Número de la semana de cada una de las 2 fechas
*/



fun main() {

    val formato = DateTimeFormatter.ofPattern("yyyy/MM/dd")


    val fecha1 = readFecha("Introduzca la primera fecha con el siguiente formato: yyyy/MM/dd", formato)
    val fecha2 = readFecha("Introduzca la segunda fecha: yyyy/MM/dd", formato)



        diferenciaFechas(fecha1,fecha2)

        principioFinFechas(fecha1,fecha2)

        numeroDiasAnio(fecha1,fecha2)

        numSemanaAnio(fecha1, fecha2)




}


fun readFecha(prompt: String, formato: DateTimeFormatter): LocalDate {
    while (true) {
        println(prompt)
        val entrada = readLine()
        if (entrada.isNullOrBlank()) {
            println("Tienes que ingresas una fecha, intentelo de nuevo.")
            continue
        }
        try {
            return LocalDate.parse(entrada, formato)
        } catch (e: DateTimeParseException) {
            println("Formato incorrecto. Debe ser yyyy/MM/dd, intentelo de nuevo.")
        }
    }
}


fun diferenciaFechas(fecha1: LocalDate, fecha2: LocalDate) {

            //funcion de kotlin
            val diferenciaDias = ChronoUnit.DAYS.between(fecha1, fecha2)

            if (fecha1!=fecha2){
                //println("Las fechas introducidas son diferentes.")
                println("La diferencia de días entre ambas fechas es de: $diferenciaDias")


            }else{
                println("Usted está en el mismo día, no hay diferencia de días.")
            }
}



fun principioFinFechas (fecha1: LocalDate, fecha2: LocalDate) {



            val principioAño1 = LocalDate.of(fecha1.year,1,1)
            val principioAño2 = LocalDate.of(fecha2.year,1,1)
            val finalAño1= LocalDate.of(fecha1.year,12,31)
            val finalAño2 = LocalDate.of(fecha2.year,12,31)

            if (fecha1!=fecha2)
                //println("Las fechas introducidas son diferentes.")
                println("Inicio y fin de año para la primera fecha es de: $principioAño1 y de $finalAño1")
                println("Inicio y fin de año para la segunda fecha es de: $principioAño2 y de $finalAño2")
}


fun numeroDiasAnio (fecha1: LocalDate, fecha2: LocalDate){


            val numDias1:Int = fecha1.lengthOfYear()
            val numDias2:Int = fecha2.lengthOfYear()


            println("El número de días del año de la primera fecha es: $numDias1")
            println("El número de días del año de la segunda fecha es: $numDias2")



}

fun numSemanaAnio (fecha1: LocalDate, fecha2: LocalDate){

            val año1 = fecha1.year
            val año2 = fecha2.year
            val semanaAnio1 = fecha1.get(WeekFields.of(Locale.getDefault()).weekOfYear())
            val semanaAnio2 = fecha2.get(WeekFields.of(Locale.getDefault()).weekOfYear())

            println("Usted está en la semana $semanaAnio1 del año $año1")

            println("Usted está en la semana $semanaAnio2 del año $año2")


}
