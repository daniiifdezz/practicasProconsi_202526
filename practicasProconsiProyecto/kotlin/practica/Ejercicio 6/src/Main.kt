import kotlin.random.Random

/*
EJERCICIO 6
El programa deberá realizar las siguientes tareas:
1.	Pedir al usuario que introduzca el número de círculos, triángulos y cuadrados que desea generar.
2.	Generar las formas geométricas con datos aleatorios para sus propiedades básicas (que se consideren oportunas), asegurándose de que sean válidos para cada tipo de figura (por ejemplo, números positivos para dimensiones).
3.	Asignar a cada figura:
o	Un color aleatorio, que se usará al mostrar la información.
o	Las coordenadas de su centro generadas de forma aleatoria (por ejemplo, dos números enteros positivos para representar su posición en un plano).
4.	Mostrar todas las formas geométricas juntas en el orden en el que fueron creadas, independientemente de su tipo.
5.	Mostrar las formas agrupadas por tipo, presentando primero los círculos, después los cuadrados y finalmente los triángulos.
6.	Para cada forma geométrica, mostrar:
o	Sus propiedades específicas (como radio, lados, base y altura, etc.).
o	El área calculada de la figura.
o	Su color asignado.
o	Las coordenadas de su centro.

 */


fun main() {

    val circulos = leerEntero("Introduce el número de círculos que desea generar: ")
    val triangulos = leerEntero("Introduce el número de triángulos que desea generar: ")
    val cuadrados = leerEntero("Introduce el número de cuadrados que desea generar: ")

    val formas = generarFormas(circulos, triangulos, cuadrados)

    imprimirPantallaFormas(formas)



}

fun leerEntero(mensaje: String): Int {
    while (true) {
        try {
            println(mensaje)
            val entrada = readln().toInt()
            return entrada
        } catch (e: NumberFormatException) {
            println("Error: Debes introducir un número válido.")
        }
    }
}




fun generarFormas(circulos:Int, triangulos:Int, cuadrados:Int): List<Forma>{
    val colores = listOf("Rojo", "Verde", "Azul", "Amarillo", "Morado")
    val rand = Random
    val formas = mutableListOf<Forma>()



    for (i in 1..circulos) {
        val radio = rand.nextInt(1, 11)
        val x = rand.nextInt(0, 100)
        val y = rand.nextInt(0, 100)
        val color = colores[rand.nextInt(colores.size)]
        formas.add(Circulo(radio, x, y, color))
    }

    for(i in 1..triangulos){
        val base = rand.nextInt(1,11)
        val altura = rand.nextInt(1,11)
        val x = rand.nextInt(0,100)
        val y = rand.nextInt(0,100 )
        val color = colores[rand.nextInt(colores.size)]
        formas.add(Triangulo(base, altura, x , y, color))
    }

    for(i in 1..cuadrados){
        val base = rand.nextInt(1,11)
        val altura = rand.nextInt(1,11)
        val x = rand.nextInt(0,100)
        val y = rand.nextInt(0,100 )
        val color = colores[rand.nextInt(colores.size)]
        formas.add(Cuadrado(base, altura, x , y, color))
    }
    return formas

}

fun imprimirPantallaFormas (formas: List<Forma>){

    println("Formas por orden: ")
    for(forma in formas){
        println ("Tipo: ${forma::class.simpleName}")
        println("Color: ${forma.color}")
        println("Coordenadas: ${forma.x}, ${forma.y})")
        println("Propiedades: ${forma.propiedades()}")
        println("Area: ${forma.calcularArea()}\n")
    }


    println("Formas agrupadas por tipo: ")
    val circulos = formas.filterIsInstance<Circulo>()
    val cuadrados = formas.filterIsInstance<Cuadrado>()
    val triangulos = formas.filterIsInstance<Triangulo>()

    for (forma in circulos) {
        println("Tipo: ${forma::class.simpleName}")
        println("Color: ${forma.color}")
        println("Coordenadas: (${forma.x}, ${forma.y})")
        println("Propiedades: ${forma.propiedades()}")
        println("Área: ${forma.calcularArea()}")
        println()
    }

     for (forma in cuadrados) {
        println("Tipo: ${forma::class.simpleName}")
        println("Color: ${forma.color}")
        println("Coordenadas: (${forma.x}, ${forma.y})")
        println("Propiedades: ${forma.propiedades()}")
        println("Área: ${forma.calcularArea()}")
        println()
    }

    for (forma in triangulos) {
        println("Tipo: ${forma::class.simpleName}")
        println("Color: ${forma.color}")
        println("Coordenadas: (${forma.x}, ${forma.y})")
        println("Propiedades: ${forma.propiedades()}")
        println("Área: ${forma.calcularArea()}")
        println()
    }

    println()

}