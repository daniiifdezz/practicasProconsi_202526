/**
 * Introducción de 2 cantidades y un tercer dato con el número de decimales en el que realizar el redondeo de las operaciones matemáticas a realizar.
 * Debe calcular:
 * -	Suma de las 2 cantidades
 * -	Resta de las 2 cantidades
 * -	División de las 2 cantidades
 * -	Multiplicación de las 2 cantidades
 * -	Módulo de la primera cantidad con la segunda
 * -	Indicar si el primer número es menor, igual o mayor al segundo número.
 *
 */

fun main() {


    //Pedir por pantalla los números
    println("Introduce el primer digito:")
    val n1 = readln().toDouble()
    println("Introduce el segundo digito:")
    val n2 = readln().toDouble()


    //val redondeo

    //Operaciones matemáticas
    val suma = n1 + n2
    val resta = n1 - n2
    val division = n2 / n1
    val multiplicacion = n1 * n2
    val modulo = n1 % n2

    //Salida por pantalla de las operaciones
    println("La suma es: $suma")
    println("La resta es: $resta")
    println("La division es: $division")
    println("La multiplicacion es: $multiplicacion")
    println("El modulo es: $modulo")


    //Comparaciones
    if (n1 > n2) {
        println("El primer numero es mayor")
    } else if (n1 < n2) {
        println("El primer numero es menor")
    } else {
        println("El primer número es igual al segundo número")
    }

}
