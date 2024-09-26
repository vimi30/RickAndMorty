package org.example.rammultiplatform.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.models.domain.Character
import com.example.network.models.domain.Episode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.rammultiplatform.components.commons.UiState
import org.example.rammultiplatform.repository.CharacterRepository
import org.example.rammultiplatform.repository.EpisodesRepository
import org.example.rammultiplatform.util.onError
import org.example.rammultiplatform.util.onSuccess

class CharacterEpisodeViewModel(
    private val characterRepository: CharacterRepository,
    private val episodesRepository: EpisodesRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<CharacterEpisodesState>(UiState.Loading)

    val viewState = _viewState.asStateFlow()

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        characterRepository.fetchCharacter(characterId)
            .onSuccess { character ->
                launch {
                    episodesRepository.fetchEpisodesByIds(character.episodeIds)
                        .onSuccess { episodes ->
                            _viewState.update {
                                return@update UiState.Success(
                                    CharacterEpisodesData(
                                        character = character,
                                        episodes = episodes
                                    )
                                )
                            }
                        }
                        .onError { error ->
                            _viewState.update {
                                return@update UiState.Error(
                                    error.message
                                )
                            }
                        }
                }
            }
    }
}

data class CharacterEpisodesData(val character: Character, val episodes: List<Episode>)
typealias CharacterEpisodesState = UiState<CharacterEpisodesData>