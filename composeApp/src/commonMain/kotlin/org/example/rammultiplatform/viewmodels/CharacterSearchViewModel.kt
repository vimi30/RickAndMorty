package org.example.rammultiplatform.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.models.domain.Character
import com.example.network.models.domain.CharacterPage
import com.example.network.models.domain.CharacterStatus
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.rammultiplatform.components.commons.UiState
import org.example.rammultiplatform.repository.CharacterRepository
import org.example.rammultiplatform.util.onError
import org.example.rammultiplatform.util.onSuccess

class CharacterSearchViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")

    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _characters = MutableStateFlow<UiState<List<Character>>>(UiState.Loading)

    private val _filteredCharacters = MutableStateFlow(listOf<Character>())
    val filteredCharacter = _filteredCharacters.asStateFlow()

    private val fetchedCharacterPages = mutableListOf<CharacterPage>()

    private val _characterStatus = MutableStateFlow<CharacterStatus?>(null)


    @OptIn(FlowPreview::class)
    val characters = searchText
        .debounce(1000L)
        .onEach {
//            _isSearching.update { true }
            _characters.update { UiState.Loading }
        }
        .combine(_characters) { text, characters ->
            if (text.isNotEmpty()) {
                fetchInitialCharacterPage()
                characters
            } else {
                UiState.Success(fetchedCharacterPages.flatMap { it.characters }.filter { it.status == _characterStatus.value })
            }
        }
//        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _characters.value
        )


    fun onSearchQueryChange(newText: String) {
        _searchText.value = newText
    }

    fun filterCharacterByStatus(characterStatus: CharacterStatus) {
        _filteredCharacters.update {
            fetchedCharacterPages.flatMap { it.characters }.filter{ it.status == characterStatus }
        }
    }

    private fun fetchInitialCharacterPage() = viewModelScope.launch {
        characterRepository.searchCharacter(searchText.value, null, null)
            .onSuccess { characterPage ->
                fetchedCharacterPages.clear()
                fetchedCharacterPages.add(characterPage)
                _characters.update {
                    UiState.Success(characterPage.characters)
                }
            }
            .onError { error ->
                _characters.update {
                    UiState.Error(error.message)
                }
            }
    }

    fun fetchNextCharacterPage() = viewModelScope.launch {
        if (fetchedCharacterPages.last().info.next.isNullOrEmpty()) return@launch
        val nextPageNumber = fetchedCharacterPages.size + 1
        characterRepository.fetchCharacterPage(pageNumber = nextPageNumber)
            .onSuccess { characterPage ->
                fetchedCharacterPages.add(characterPage)
                _characters.update { currentState ->
                    val currentCharacters = (currentState as? UiState.Success)?.data ?: emptyList()
                    return@update UiState.Success(data = currentCharacters + characterPage.characters)
                }
            }
            .onError { error ->
                _characters.update {
                    UiState.Error(error.message)
                }
            }
    }
}