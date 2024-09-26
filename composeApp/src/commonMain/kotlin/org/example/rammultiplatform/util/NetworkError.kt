package org.example.rammultiplatform.util

enum class NetworkError(val message: String) : Error {
    REQUEST_TIMEOUT("The request timed out. Please check your internet connection and try again."),
    UNAUTHORIZED("Unauthorized access. Please log in to continue."),
    CONFLICT("A conflict occurred while processing your request. Please refresh the page or try again later."),
    TOO_MANY_REQUESTS("You're making requests too quickly. Please wait a moment and try again."),
    NO_INTERNET("No internet connection detected. Please check your network settings and try again."),
    PAYLOAD_TOO_LARGE("The data you're trying to send is too large. Please reduce its size and try again."),
    SERVER_ERROR("A server error occurred. Please try again later."),
    SERIALIZATION("An error occurred while processing data. Please try again."),
    NOT_FOUND("The requested resource was not found."),
    UNKNOWN("An unexpected error occurred. Please try again.")
}