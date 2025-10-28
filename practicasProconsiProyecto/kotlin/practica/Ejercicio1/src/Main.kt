/**
 * Introducción de 2 cantidades y un tercer dato con el número de decimales en el que realizar el redondeo de las operaciones matemáticas a realizar.
 * Debe calcular:
 * - Suma de las 2 cantidades
 * - Resta de las 2 cantidades
 * - División de las 2 cantidades
 * - Multiplicación de las 2 cantidades
 * - Módulo de la primera cantidad con la segunda
 * - Indicar si el primer número es menor, igual o mayor al segundo número.
 */


fun main() {
        // Pedir por pantalla los números
        val n1 = nValido("Introduce un número valido.")
        val n2 = nValido("Introduce un 2º número valido.")


        val decimales = nValidoDecimales("Introduce un número valido para redondear.").toInt()

        // Llamadas a métodos y salida por pantalla
        println("La suma es: ${sumar(n1, n2, decimales)}")
        println("La resta es: ${restar(n1, n2, decimales)}")
        println("La multiplicacion es: ${multiplicar(n1, n2, decimales)}")
        println("La division es: ${dividir(n1, n2, decimales)}")
        println("El modulo es: ${modulo(n1, n2, decimales)}")
        println(comparar(n1, n2))

}


fun sumar(a: Double, b: Double, decimales: Int): String {
    return "%.${decimales}f".format(a + b)
}

fun restar(a: Double, b: Double, decimales: Int): String {
    return "%.${decimales}f".format(a - b)
}

fun multiplicar(a: Double, b: Double, decimales: Int): String {
    return "%.${decimales}f".format(a * b)
}

fun dividir(a: Double, b: Double, decimales: Int): String {
    if (b == 0.0) return "Error: División por cero"
    return "%.${decimales}f".format(a / b)
}

fun modulo(a: Double, b: Double, decimales: Int): String {
    if (b == 0.0) return "Error: Módulo por cero"
    return "%.${decimales}f".format(a % b)
}

fun comparar(a: Double, b: Double): String {

    //posibilidad de dejar el if anterior
    return when {
        a > b -> "El primer número es mayor"
        a < b -> "El primer número es menor"
        else -> "El primer número es igual al segundo número"
    }
}

fun nValido(std: String): Double{
    var numero: Double?
    println("Introduce un número válido.")

    do{

        val input = readln()
        numero = input.toDoubleOrNull()

        when (numero) {
            is Double -> return numero
            else -> println("Debe introducir un formato numérico.")

        }
    }while(true)


}

fun nValidoDecimales(std:String): Int{

    var decimales: Int?
    println("Introduce un número para redondeo válido.")


    do{
        var input = readln()

        decimales = input.toIntOrNull()

        when {
            decimales !=null && decimales >=0 -> {
                return decimales
            }
            decimales == null -> {
                println("Introduzca un al menos un número para el redondeo, no puedes meter una cadena vacía.")
            }
            else -> println("Debes ingresar un número positivo para el redondeo.")

        }


    }while (true)
}