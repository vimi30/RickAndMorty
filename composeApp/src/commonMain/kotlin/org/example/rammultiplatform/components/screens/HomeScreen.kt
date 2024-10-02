package org.example.rammultiplatform.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.network.models.domain.Character
import kotlinx.coroutines.flow.StateFlow
import org.example.rammultiplatform.components.character.CharacterGridItem
import org.example.rammultiplatform.components.commons.ErrorComponent
import org.example.rammultiplatform.components.commons.LoadingState
import org.example.rammultiplatform.components.commons.SimpleToolBar
import org.example.rammultiplatform.components.commons.UiState
import org.example.rammultiplatform.util.PortalGreen


@Composable
fun HomeScreen(
    onCharacterSelected: (Int) -> Unit,
    screenState: StateFlow<UiState<List<Character>>>,
    fetchInitialCharacterPage: () -> Unit,
    fetchNextCharacterPage: () -> Unit
) {
    val viewState by screenState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit, block = {
        fetchInitialCharacterPage()
    })

    val scrollState = rememberLazyGridState()

    val fetchNextPage: Boolean by remember {
        derivedStateOf {
            val currentCharacterCount =
                (viewState as? UiState.Success)?.data?.size
                    ?: return@derivedStateOf false

            val lastDisplayedIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: return@derivedStateOf false

            return@derivedStateOf lastDisplayedIndex >= currentCharacterCount - 10
        }
    }

    LaunchedEffect(key1 = fetchNextPage, block = {
        if (fetchNextPage) fetchNextCharacterPage()
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PortalGreen)
    ) {
        SimpleToolBar(
            title = "All Characters",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .background(color = PortalGreen)
        )
        when (val state = viewState) {
            is UiState.Success -> {
                LazyVerticalGrid(
                    state = scrollState,
                    contentPadding = PaddingValues(all = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    columns = GridCells.Fixed(2),
                    content = {
                        items(
                            items = state.data,
                            key = { it.hashCode() }
                        ) { character ->
                            CharacterGridItem(modifier = Modifier, character = character) {
                                onCharacterSelected(character.id)
                            }
                        }
                    }
                )
            }

            UiState.Loading -> LoadingState()
            is UiState.Error -> {
                ErrorComponent(errorMessage = state.errorMessage)
            }
        }


    }

}