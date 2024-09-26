package org.example.rammultiplatform.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.models.domain.Character
import org.example.rammultiplatform.components.commons.DataPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.rammultiplatform.components.commons.UiState
import org.example.rammultiplatform.repository.CharacterRepository
import org.example.rammultiplatform.util.onError
import org.example.rammultiplatform.util.onSuccess

class CharacterDetailsViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _screenState = MutableStateFlow<CharacterDetailsState>(
        value = UiState.Loading
    )

    val screenState = _screenState.asStateFlow()

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        _screenState.update {
            return@update UiState.Loading
        }
        characterRepository.fetchCharacter(characterId)
            .onSuccess { character ->
                val dataPoints = buildList {
                    add(DataPoint("Last Known location", character.location.name))
                    add(DataPoint("Species", character.species))
                    add(DataPoint("Gender", character.gender.displayName))
                    character.type.takeIf { it.isNotEmpty() }?.let { type ->
                        add(DataPoint("Type", type))
                    }
                    add(DataPoint("Origin", character.origin.name))
                    add(DataPoint("Episode count", character.episodeIds.size.toString()))
                }
                _screenState.update {
                    return@update UiState.Success(
                        data = CharacterDetailData(
                            character = character,
                            characterDataPoint = dataPoints
                        )

                    )
                }
            }
            .onError { exception ->
                _screenState.update {
                    return@update UiState.Error(
                        errorMessage = exception.message
                    )
                }
            }
    }
}

data class CharacterDetailData(val character: Character, val characterDataPoint: List<DataPoint>)
typealias CharacterDetailsState = UiState<CharacterDetailData>
