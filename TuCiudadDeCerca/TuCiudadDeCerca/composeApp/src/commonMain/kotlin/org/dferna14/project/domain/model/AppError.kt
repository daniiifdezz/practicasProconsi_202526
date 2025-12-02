package org.dferna14.project.domain.model

sealed class AppError(override val message: String) : Exception(message) {
    class NetworkError(customMessage: String = "Error de red. Comprueba tu conexión.") : AppError(customMessage)
    class ServerError(customMessage: String = "Error del servidor. Inténtalo más tarde.") : AppError(customMessage)
    class UnknownError(customMessage: String = "Ha ocurrido un error inesperado.") : AppError(customMessage)
}