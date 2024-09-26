package org.example.rammultiplatform

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import org.example.rammultiplatform.components.screens.CharacterDetailsScreen
import org.example.rammultiplatform.components.screens.CharacterEpisodeScreen
import org.example.rammultiplatform.components.screens.EpisodeDetailScreen
import org.example.rammultiplatform.components.screens.EpisodeScreen
import org.example.rammultiplatform.components.screens.HomeScreen
import org.example.rammultiplatform.components.screens.Screens
import org.example.rammultiplatform.components.screens.CharacterSearchScreen
import org.example.rammultiplatform.util.BrightYellow
import org.example.rammultiplatform.util.PortalGreen
import org.example.rammultiplatform.util.getAsyncImageLoader
import org.example.rammultiplatform.viewmodels.CharacterDetailsViewModel
import org.example.rammultiplatform.viewmodels.CharacterEpisodeViewModel
import org.example.rammultiplatform.viewmodels.CharacterSearchViewModel
import org.example.rammultiplatform.viewmodels.EpisodeDetailsViewModel
import org.example.rammultiplatform.viewmodels.EpisodesViewModel
import org.example.rammultiplatform.viewmodels.HomeScreenViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }
        KoinContext {
            val navController = rememberNavController()
            val items = listOf(
                Screens.Home, Screens.Episodes, Screens.Search
            )
            var selectedIndex by remember {
                mutableIntStateOf(0)
            }
            Scaffold(
                containerColor = PortalGreen,
                bottomBar = {
                    NavigationBar(
                        tonalElevation = 8.dp,
                        containerColor = BrightYellow,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    ) {
                        items.forEachIndexed { index, screen ->
                            NavigationBarItem(
                                icon = {
                                    screen.icon?.let {
                                        Icon(
                                            it,
                                            contentDescription = null
                                        )
                                    }
                                },
                                label = { Text(screen.title) },
                                selected = index == selectedIndex,
                                onClick = {
                                    selectedIndex = index
                                    navController.navigate(screen.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.findStartDestination().displayName) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // re-selecting the same item
                                        launchSingleTop = true
                                        // Restore state when re-selecting a previously selected item
                                        restoreState = true
                                    }
                                },
                            )
                        }
                    }
                }
            ) { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = Screens.Home.route,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable(Screens.Home.route) {
                        val viewModel = koinViewModel<HomeScreenViewModel>()
                        HomeScreen(
                            onCharacterSelected = { characterId ->
                                navController.navigate(
                                    Screens.CharacterDetails.createRoute(
                                        characterId
                                    )
                                )
                            },
                            screenState = viewModel.viewState,
                            viewModel::fetchInitialCharacterPage,
                            viewModel::fetchNextCharacterPage
                        )
                    }

                    composable(
                        route = Screens.CharacterDetails.route,
                        arguments = listOf(navArgument("characterId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val viewModel = koinViewModel<CharacterDetailsViewModel>()
                        val characterId: Int =
                            backStackEntry.arguments?.getInt("characterId", 0) ?: -1
                        CharacterDetailsScreen(
                            characterId = characterId,
                            onEpisodeClicked = {
                                navController.navigate(
                                    Screens.CharacterEpisodes.createRoute(
                                        characterId
                                    )
                                )
                            },
                            onBackClicked = { navController.navigateUp() },
                            screenState = viewModel.screenState,
                            fetchCharacter = viewModel::fetchCharacter
                        )
                    }

                    composable(
                        route = Screens.CharacterEpisodes.route,
                        arguments = listOf(navArgument("characterId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val viewModel = koinViewModel<CharacterEpisodeViewModel>()
                        val characterId: Int =
                            backStackEntry.arguments?.getInt("characterId", 0) ?: -1
                        CharacterEpisodeScreen(
                            characterId = characterId,
                            onBackClicked = { navController.navigateUp() },
                            screenState = viewModel.viewState,
                            fetchCharacter = viewModel::fetchCharacter
                        )

                    }
                    composable(
                        route = Screens.Episodes.route
                    ) {
                        val viewModel = koinViewModel<EpisodesViewModel>()
                        EpisodeScreen(
                            onEpisodeSelected = { episodeId ->
                            navController.navigate(Screens.EpisodesDetails.createRoute(episodeId))
                            },
                            screenState = viewModel.viewState,
                            fetchInitialEpisodePage = viewModel::fetchInitialEpisodePage,
                            fetchNextEpisodePage = viewModel::fetchNextEpisodePage
                        )
                    }

                    composable(
                        route = Screens.Search.route
                    ) {
                        val viewModel = koinViewModel<CharacterSearchViewModel>()
                        CharacterSearchScreen(
                            onItemClicked = { characterId ->
                                navController.navigate(Screens.CharacterDetails.createRoute(characterId))
                            },
                            charactersState = viewModel.characters,
                            fetchNextCharacterPage = viewModel::fetchNextCharacterPage,
                            filterCharacterByStatus = viewModel::filterCharacterByStatus,
                            filteredCharactersState = viewModel.filteredCharacter,
                            searchTextState = viewModel.searchText,
                            isSearchingState = viewModel.isSearching,
                            onSearchQueryChange = viewModel::onSearchQueryChange
                        )
                    }

                    composable(
                        route = Screens.EpisodesDetails.route,
                        arguments = listOf(navArgument("episodeId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val viewModel = koinViewModel<EpisodeDetailsViewModel>()
                        val episodeId: Int =
                            backStackEntry.arguments?.getInt("episodeId", 0) ?: -1
                        EpisodeDetailScreen(
                            episodeId,
                            onBackClicked = { navController.navigateUp() },
                            onCharacterSelected = { characterId ->
                                navController.navigate(Screens.CharacterDetails.createRoute(characterId))
                            },
                            screenState = viewModel.viewState,
                            fetchEpisode = viewModel::fetchEpisode
                        )
                    }
                }
            }

        }
    }
}

@Composable
inline fun <reified T : ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}
