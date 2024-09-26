package org.example.rammultiplatform.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.models.domain.Character
import com.example.network.models.domain.CharacterPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.rammultiplatform.components.commons.UiState
import org.example.rammultiplatform.repository.CharacterRepository
import org.example.rammultiplatform.util.onError
import org.example.rammultiplatform.util.onSuccess

class HomeScreenViewModel(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow<UiState<List<Character>>>(UiState.Loading)

    val viewState: StateFlow<UiState<List<Character>>> = _viewState.asStateFlow()

    private val fetchedCharacterPages = mutableListOf<CharacterPage>()

    fun fetchInitialCharacterPage() = viewModelScope.launch {
        if (fetchedCharacterPages.isNotEmpty()) return@launch
        repository.fetchCharacterPage(pageNumber = 1)
            .onSuccess { characterPage ->
                fetchedCharacterPages.clear()
                fetchedCharacterPages.add(characterPage)

                _viewState.update {
                    return@update UiState.Success(
                        data = characterPage.characters
                    )
                }
            }
            .onError { error ->
                _viewState.update {
                    return@update UiState.Error(error.message)
                }
            }
    }

    fun fetchNextCharacterPage() = viewModelScope.launch {
        if( fetchedCharacterPages.last().info.next.isNullOrEmpty()) return@launch
        val nextPageNumber = fetchedCharacterPages.size + 1
        repository.fetchCharacterPage(pageNumber = nextPageNumber)
            .onSuccess { characterPage ->
                fetchedCharacterPages.add(characterPage)
                _viewState.update { currentViewState ->
                    val currentCharacters =
                        (currentViewState as? UiState.Success)?.data
                            ?: emptyList()
                    return@update UiState.Success(data = currentCharacters + characterPage.characters)
                }
            }
            .onError { error ->
                _viewState.update {
                    return@update UiState.Error(error.message)
                }
            }
    }
}