package org.example.rammultiplatform.components.commons

sealed interface UiState<out T>{
    data object Loading : UiState<Nothing>
    data class Success<out T>(val data: T) : UiState<T>
    data class Error(val errorMessage: String) : UiState<Nothing>
}