class Triangulo(val base: Int, val altura: Int, x: Int, y: Int, color: String) : Forma(x, y, color) {
    override fun calcularArea(): Double = 0.5 * base * altura
    override fun propiedades(): String = "Base: $base, Altura: $altura"
}
