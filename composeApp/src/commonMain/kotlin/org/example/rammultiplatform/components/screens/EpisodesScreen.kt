package org.example.rammultiplatform.components.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.network.models.domain.Episode
import com.example.ram.components.episode.EpisodeListItem
import kotlinx.coroutines.flow.StateFlow
import org.example.rammultiplatform.components.commons.ErrorComponent
import org.example.rammultiplatform.components.commons.LoadingState
import org.example.rammultiplatform.components.commons.UiState
import org.example.rammultiplatform.util.LightGreen


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EpisodeScreen(
    onEpisodeSelected: (Int) -> Unit,
    screenState: StateFlow<UiState<List<Episode>>>,
    fetchInitialEpisodePage: () -> Unit,
    fetchNextEpisodePage: () -> Unit
) {
    val viewState by screenState.collectAsState()

    val scrollState = rememberLazyListState()

    var seasonEpisodes: Map<Int, List<Episode>> by remember { mutableStateOf(emptyMap()) }

    val fetchNextPage: Boolean by remember {
        derivedStateOf {
            val currentEpisodesCount =
                (viewState as? UiState.Success)?.data?.size
                    ?: return@derivedStateOf false

            val lastDisplayedIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: return@derivedStateOf false

            return@derivedStateOf lastDisplayedIndex >= currentEpisodesCount - 10
        }
    }

    LaunchedEffect(key1 = fetchNextPage) {
        if (fetchNextPage) fetchNextEpisodePage()
    }

    LaunchedEffect(key1 = Unit) {
        fetchInitialEpisodePage()
    }

    when (val state = viewState) {

        is UiState.Loading -> LoadingState()
        is UiState.Success -> {
            seasonEpisodes = state.data.groupBy { it.seasonNumber }
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = LightGreen)
            ) {

                seasonEpisodes.forEach { (season, episodes) ->

                    stickyHeader { SeasonHeader(seasonNumber = season) }
                    items(episodes) { episode ->
                        EpisodeListItem(episode = episode) {
                            onEpisodeSelected(episode.id)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }

                }

            }
        }
        is UiState.Error -> {
            ErrorComponent(state.errorMessage)
        }
    }
}
