package org.example.rammultiplatform.components.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow
import org.example.rammultiplatform.components.character.CharacterGridItem
import org.example.rammultiplatform.components.commons.ErrorComponent
import org.example.rammultiplatform.components.commons.LoadingState
import org.example.rammultiplatform.components.commons.SimpleToolBar
import org.example.rammultiplatform.components.commons.UiState
import org.example.rammultiplatform.components.episode.EpisodeDetailNameComponent
import org.example.rammultiplatform.util.RichPurple
import org.example.rammultiplatform.viewmodels.EpisodeDetailState

@Composable
fun EpisodeDetailScreen(
    episodeId: Int,
    onBackClicked: () -> Unit,
    onCharacterSelected: (Int) -> Unit,
    screenState: StateFlow<EpisodeDetailState>,
    fetchEpisode: (Int) -> Unit
) {
    val viewState by screenState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        fetchEpisode(episodeId)
    }
    Column {

        when (val state = viewState) {
            is UiState.Error -> {
                ErrorComponent(state.errorMessage)
            }

            UiState.Loading -> {
                LoadingState()
            }

            is UiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    SimpleToolBar(title = "Episode Details", onBackAction = onBackClicked)
                    Spacer(modifier = Modifier.height(8.dp))
                    EpisodeDetailNameComponent(state.data.episode)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (state.data.characters.isNotEmpty()) {
                        Text(
                            text = "Characters",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = RichPurple,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyVerticalGrid(
                            contentPadding = PaddingValues(all = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            columns = GridCells.Fixed(2),
                            content = {
                                items(
                                    items = state.data.characters,
                                    key = { it.hashCode() }
                                ) { character ->
                                    CharacterGridItem(modifier = Modifier, character = character) {
                                        onCharacterSelected(character.id)
                                    }
                                }
                            }
                        )
                    }

                }

            }
        }
    }
}