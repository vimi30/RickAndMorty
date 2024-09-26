package org.example.rammultiplatform.components.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.network.models.domain.Character
import com.example.network.models.domain.CharacterStatus
import kotlinx.coroutines.flow.StateFlow
import org.example.rammultiplatform.components.character.CharacterSearchItem
import org.example.rammultiplatform.components.character.SearchFilterChipComponent
import org.example.rammultiplatform.components.commons.ErrorComponent
import org.example.rammultiplatform.components.commons.LoadingState
import org.example.rammultiplatform.components.commons.UiState
import org.example.rammultiplatform.util.BrightYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterSearchScreen(
    onItemClicked: (Int) -> Unit,
    searchTextState: StateFlow<String>,
    isSearchingState: StateFlow<Boolean>,
    charactersState: StateFlow<UiState<List<Character>>>,
    filteredCharactersState: StateFlow<List<Character>>,
    fetchNextCharacterPage: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    filterCharacterByStatus: (CharacterStatus) -> Unit,
) {
    val searchText by searchTextState.collectAsState()
    val isSearching by isSearchingState.collectAsState()
    val characters by charactersState.collectAsState()

    val filteredCharacters by filteredCharactersState.collectAsState()

    var aliveSelected by remember { mutableStateOf(false) }

    var deadSelected by remember { mutableStateOf(false) }

    var unknownSelected by remember { mutableStateOf(false) }

    val scrollState = rememberLazyListState()

    val fetchNextPage: Boolean by remember {
        derivedStateOf {
            val currentCharacterCount = (characters as? UiState.Success)?.data?.size ?: 0

            val lastDisplayedIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: return@derivedStateOf false

            return@derivedStateOf lastDisplayedIndex >= currentCharacterCount - 10
        }
    }
    LaunchedEffect(
        key1 = fetchNextPage
    ) {
        if (fetchNextPage) fetchNextCharacterPage()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSearching) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.width(2.dp))
            }

            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                active = isSearching,
                query = searchText,
                onSearch = { },
                onQueryChange = { onSearchQueryChange(it) },
                onActiveChange = { },
                tonalElevation = 6.dp,
                windowInsets = WindowInsets(top = 0.dp),
                shadowElevation = 6.dp,
                placeholder = { Text("Search Character") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                colors = SearchBarDefaults.colors(
                    BrightYellow
                ),
                content = {},
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            SearchFilterChipComponent(
                aliveSelected,
                CharacterStatus.Alive.displayName,
                onSelected = {
                    aliveSelected = !aliveSelected
                    deadSelected = false
                    unknownSelected = false
                    filterCharacterByStatus(CharacterStatus.Alive)
                }
            )
            Spacer(modifier = Modifier.width(2.dp))
            SearchFilterChipComponent(
                deadSelected,
                CharacterStatus.Dead.displayName,
                onSelected = {
                    deadSelected = !deadSelected
                    aliveSelected = false
                    unknownSelected = false
                    filterCharacterByStatus(CharacterStatus.Dead)
                }
            )
            Spacer(modifier = Modifier.width(2.dp))
            SearchFilterChipComponent(
                unknownSelected,
                CharacterStatus.Unknown.displayName,
                onSelected = {
                    unknownSelected = !unknownSelected
                    aliveSelected = false
                    deadSelected = false
                    filterCharacterByStatus(CharacterStatus.Unknown)
                }
            )
        }

        when (val state = characters) {
            is UiState.Error -> {
                ErrorComponent(state.errorMessage)
            }

            UiState.Loading -> {
                LoadingState(modifier = Modifier.size(24.dp))
            }

            is UiState.Success -> {
                LazyColumn(
                    state = scrollState
                ) {
                    items(
                        if (aliveSelected || deadSelected || unknownSelected) {
                            filteredCharacters
                        } else {
                            state.data
                        }
                    ) { character ->
                        CharacterSearchItem(character) {
                            onItemClicked(character.id)
                        }
                    }
                }
            }
        }
    }
}