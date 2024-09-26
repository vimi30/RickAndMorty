package org.example.rammultiplatform.components.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow
import org.example.rammultiplatform.components.character.CharacterImageComponent
import org.example.rammultiplatform.components.character.CharacterNameComponent
import org.example.rammultiplatform.components.commons.EpisodeRowComponent
import org.example.rammultiplatform.components.commons.ErrorComponent
import org.example.rammultiplatform.components.commons.LoadingState
import org.example.rammultiplatform.components.commons.SimpleToolBar
import org.example.rammultiplatform.components.commons.UiState
import org.example.rammultiplatform.util.PortalGreen
import org.example.rammultiplatform.util.RichPurple
import org.example.rammultiplatform.viewmodels.CharacterEpisodesState

@Composable
fun CharacterEpisodeScreen(
    characterId: Int,
    onBackClicked: () -> Unit,
    fetchCharacter: (Int) -> Unit,
    screenState: StateFlow<CharacterEpisodesState>
) {

    val currentViewState by screenState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        fetchCharacter(characterId)
    })

    MainScreen(
        currentViewState,
        onBakClicked = onBackClicked
    )

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreen(currentViewState: CharacterEpisodesState, onBakClicked: () -> Unit) {

    Column {
        SimpleToolBar(
            title = "Character Episodes",
            onBackAction = { onBakClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)

        )
        when (currentViewState) {
            is UiState.Error -> {
                ErrorComponent(
                    currentViewState.errorMessage
                )
            }

            UiState.Loading -> {
                LoadingState()
            }

            is UiState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item { CharacterNameComponent(name = currentViewState.data.character.name) }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    item { CharacterImageComponent(imageUrl = currentViewState.data.character.imageUrl) }

                    currentViewState.data.episodes.groupBy { it.seasonNumber }
                        .forEach { (season, episodeList) ->
                            stickyHeader { SeasonHeader(season) }
                            items(episodeList) { episode ->
                                EpisodeRowComponent(episode = episode)
                                HorizontalDivider()
                            }
                        }
                }

            }
        }
    }
}

@Composable
fun SeasonHeader(seasonNumber: Int) {

    ElevatedCard(
        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PortalGreen)
        ) {

            Text(
                text = "Season $seasonNumber",
                color = RichPurple,
                fontSize = 32.sp,
                lineHeight = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    }
}