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
    println("Introduce el primer digito:")
    val n1 = readln().toDouble()
    println("Introduce el segundo digito:")
    val n2 = readln().toDouble()

    // Para el redondeo
    println("Introduce el número de digitos para redondear:")
    val decimales = readln().toInt()

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
