class Circulo(val radio: Int, x: Int, y: Int, color: String) : Forma(x, y, color) {
    override fun calcularArea(): Double = Math.PI * radio * radio
    override fun propiedades(): String = "Radio: $radio"
}
