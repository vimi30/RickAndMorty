package org.example.rammultiplatform.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.models.domain.Episode
import com.example.network.models.domain.EpisodePage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.rammultiplatform.components.commons.UiState
import org.example.rammultiplatform.repository.EpisodesRepository
import org.example.rammultiplatform.util.onError
import org.example.rammultiplatform.util.onSuccess


class EpisodesViewModel(
    private val episodesRepository: EpisodesRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<UiState<List<Episode>>>(UiState.Loading)

    val viewState: StateFlow<UiState<List<Episode>>> = _viewState.asStateFlow()

    private val fetchedEpisodePages = mutableListOf<EpisodePage>()

    fun fetchInitialEpisodePage() = viewModelScope.launch {
        if (fetchedEpisodePages.isNotEmpty()) return@launch
        episodesRepository.fetchEpisodePage(1)
            .onSuccess { episodePage ->
                fetchedEpisodePages.clear()
                fetchedEpisodePages.add(episodePage)
                _viewState.update {
                    return@update UiState.Success(
                        data = episodePage.episodes
                    )
                }
            }
            .onError { exception ->
                _viewState.update {
                    return@update UiState.Error(exception.message)
                }
            }
    }

    fun fetchNextEpisodePage() = viewModelScope.launch {
        if(fetchedEpisodePages.last().info.next.isNullOrEmpty()) return@launch
        val nextPageNumber = fetchedEpisodePages.size + 1
        episodesRepository.fetchEpisodePage(nextPageNumber)
            .onSuccess { nextEpisodePage ->
                fetchedEpisodePages.add(nextEpisodePage)
                _viewState.update { currentViewState ->
                    val currentEpisodes =
                        (currentViewState as? UiState.Success)?.data
                            ?: emptyList()
                    return@update UiState.Success(
                        data = currentEpisodes + nextEpisodePage.episodes
                    )
                }
            }
            .onError { error ->
                _viewState.update {
                    return@update UiState.Error(error.message)
                }
            }
    }

}
