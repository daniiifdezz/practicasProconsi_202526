/**
 * ItemSeparator es una clase que filtra una determinada cadena de entrada sin procesar con el siguiente formato
 * "ItemName $$ ## ItemPrice $$ ## ItemQuantity" y lo almacena como estos atributos de clase:
 * -	name (String)
 * -	price (Double)
 * -	quantity (Integer)
 * Implementa el constructor ItemsSeparator(String rawInput) y los getters para los 3 atributos.
 * Clase main para realizar la prueba:
 * String stdIn = “Bread$$##12.5$$##10”; // ItemName$$##ItemPrice$$##ItemQuantity
 * Ejemplo de salida:
 * Item Name: Bread
 * Item Price: 12.5 Item
 * Quantity: 10
 *
 */

class ItemSeparator (val rawInput: String){
    val name: String
    val price: Double
    val quantity: Int

    init{
        val parts = rawInput.split ("$$##")
        name = parts[0].trim()
        price = parts [1].toDouble()
        quantity = parts [2].toInt()
    }

}

fun main(){

    val stdIn = "Bread$$##12.5$$##10"
    val item = ItemSeparator(stdIn)
    println("Item Name: ${item.name}")
    println("Item Price: ${item.price} Item")
    println("Quantity: ${item.quantity}")

}