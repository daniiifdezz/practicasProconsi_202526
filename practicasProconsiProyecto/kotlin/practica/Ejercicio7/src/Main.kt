/**
 * Dado un número indicar si es número válido Kaprekar.
 * Dependiendo el modo de planteamiento de cómo resolverlo indicar
 * el número de operaciones realizadas para obtener el resultado final.
 *
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    println("Introduce un número para saber si es Kaprekar: ")
    val numero = readln().toInt()

    val (esKaprekar, operaciones) = esKaprekar(numero)
    if (esKaprekar) {
        println("El número $numero es Kaprekar.")
    } else {
        println("El número $numero no es Kaprekar.")
    }

    println("Número de operaciones realizadas: $operaciones")

}

fun esKaprekar(n:Int): Pair <Boolean, Int> {
    var operaciones = 0
    val cuadrado = n * n

    operaciones ++

    if (n < 1) {
        return Pair(false, operaciones)
    }


    val strCuadrado = cuadrado.toString()

    for (i in 1 until strCuadrado.length){
        val pIzquierda = strCuadrado.substring(0, i).toInt()
        val pDerecha = strCuadrado.substring(i).toInt()
        operaciones +=2

        if (pIzquierda + pDerecha == n) {
            operaciones ++
            return Pair(true, operaciones)
        }
        operaciones ++
    }

    return Pair(false, operaciones)
}