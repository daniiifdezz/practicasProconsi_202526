
/**
 * Con el siguiente texto de ejemplo mostrar:
 * -	Número de caracteres
 * -	Pasarlo a mayúsculas
 * -	Pasarlo a minúsculas
 * -	Número de palabras repetidas separado por espacios e indicar cuáles son esas palabras repetidas. Es decir, si hola y banco se repiten en el texto tienes que mostrar hola y banco.
 * -	Reemplazar la palabra “Proconsi” por “Isnocorp” y mostrar el texto
 * -	Concatenar el texto 1000 veces y mostrar el tiempo tardado en realizar dicha concatenación y la longitud final del texto. (No mostréis el texto final)
 * String texto = “Proconsi es una empresa de Tecnologías de la Información y la Comunicación especializada en el desarrollo e integración de soluciones informáticas para todo tipo de empresas. Más de tres décadas de experiencia avalan a una compañía tan flexible como responsable. Cuenta con un equipo multidisciplinar de más de 120 profesionales cualificados, expertos y comprometidos con un único objetivo: hallar la solución tecnológica exacta para cada cliente. Proconsi es especialista en la creación y el desarrollo de software de gestión, consultoría tecnológica, dirección y gestión de proyectos I+D+i basados en TIC, soporte técnico, aplicaciones móviles y fomento de tendencias en nuevas tecnologías, como el cloud computing.”
 *
 */
fun main() {

    val text:String = "Proconsi es una empresa de Tecnologías de la Información y la Comunicación especializada en el desarrollo e integración de soluciones informáticas para todo tipo de empresas. Más de tres décadas de experiencia avalan a una compañía tan flexible como responsable. Cuenta con un equipo multidisciplinar de más de 120 profesionales cualificados, expertos y comprometidos con un único objetivo: hallar la solución tecnológica exacta para cada cliente. Proconsi es especialista en la creación y el desarrollo de software de gestión, consultoría tecnológica, dirección y gestión de proyectos I+D+i basados en TIC, soporte técnico, aplicaciones móviles y fomento de tendencias en nuevas tecnologías, como el cloud computing."

    val characters:Int = text.length
    val text_mayusculas = text.uppercase()
    val text_minusculas = text.lowercase()

    println("El número de caracteres que hay en el texto es de $characters")
    println("El texto en mayusculas es: $text_mayusculas")
    println("El texto en minusculas es: $text_minusculas")

    val palabras = text_minusculas.split(" ", ".", ",", ":", ";")

    val contador = mutableMapOf<String, Int>()

    val repetidas = mutableListOf<String>()


    for (palabra in palabras) {
        if(palabra.isNotBlank()){
            contador[palabra] = contador.getOrDefault(palabra, 0) + 1
        }else{
            contador[palabra] = 1
        }

        //creamos lista para las palabras repetidas
        for((palabra, cantidad) in contador){

            //sin !in repetidas, se metian en la lista de repetidas, las mismas palabras muchas veces
            if (cantidad > 1 && palabra !in repetidas){
                repetidas.add(palabra)
            }
        }
    }
    println("El número de palabras repetidas es: ${repetidas.size}")
    println("Las palabras repetidas son: ${repetidas.joinToString(" ")}")




}


