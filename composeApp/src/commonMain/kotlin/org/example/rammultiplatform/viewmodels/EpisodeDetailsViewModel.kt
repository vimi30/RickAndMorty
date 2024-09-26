package org.example.rammultiplatform.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.models.domain.Character
import com.example.network.models.domain.Episode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.rammultiplatform.components.commons.UiState
import org.example.rammultiplatform.repository.CharacterRepository
import org.example.rammultiplatform.repository.EpisodesRepository
import org.example.rammultiplatform.util.onError
import org.example.rammultiplatform.util.onSuccess

class EpisodeDetailsViewModel(
    private val episodesRepository: EpisodesRepository,
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _viewState =
        MutableStateFlow<EpisodeDetailState>(UiState.Loading)

    val viewState: StateFlow<UiState<EpisodeDetailData>> = _viewState.asStateFlow()

    fun fetchEpisode(episodeId: Int) = viewModelScope.launch {
        episodesRepository.fetchEpisode(episodeId)
            .onSuccess { episode ->
                launch {
                    characterRepository.fetchCharactersByIds(episode.characterIdsInEpisode)
                        .onSuccess { characters ->
                            _viewState.update {
                                return@update UiState.Success(
                                    EpisodeDetailData(
                                        episode, characters
                                    )
                                )
                            }
                        }
                        .onError { exception ->
                            _viewState.update {
                                return@update UiState.Error(
                                    errorMessage = exception.message
                                )
                            }
                        }
                }
            }
            .onError { error ->
                _viewState.update {
                    return@update UiState.Error(
                        errorMessage = error.message
                    )
                }
            }
    }
}

data class EpisodeDetailData(val episode: Episode, val characters: List<Character>)
typealias EpisodeDetailState = UiState<EpisodeDetailData>
