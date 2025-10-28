package util

object DniUtils {
    private const val LETTERS = "TRWAGMYFPDXBNJZSQVHLCKE"

    fun isValidDni(raw: String): Boolean {
        val s = raw.uppercase().replace("\\s".toRegex(), "")
        val dniRegex = Regex("^[0-9]{8}[A-Z]\$")
        val nieRegex = Regex("^[XYZ][0-9]{7}[A-Z]\$")

        try {
            return when {
                dniRegex.matches(s) -> {
                    val num = s.substring(0, 8).toInt()
                    LETTERS[num % 23] == s[8]
                }
                nieRegex.matches(s) -> {
                    val prefix = when (s[0]) {
                        'X' -> '0'
                        'Y' -> '1'
                        'Z' -> '2'
                        else -> '0'
                    }
                    val numStr = prefix + s.substring(1, 8)
                    val num = numStr.toInt()
                    LETTERS[num % 23] == s[8]
                }
                else -> false
            }
        } catch (e: Exception) {
            return false
        }
    }
}