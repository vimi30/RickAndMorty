package org.example.rammultiplatform.components.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val title: String, val route: String, val icon: ImageVector?) {
    data object Home : Screens(title = "Home", route = "home_screen", icon = Icons.Filled.Home)
    data object Episodes : Screens(title = "Episodes", route = "episodes_screen", icon = Icons.Filled.PlayArrow)
    data object Search : Screens(title = "Search", route = "search", icon = Icons.Filled.Search)
    data object CharacterDetails : Screens(title = "Character Details", route = "character_details/{characterId}", icon = null){
        fun createRoute(characterId: Int): String {
            return "character_details/$characterId"
        }
    }

    data object CharacterEpisodes : Screens(title = "Character Details", route = "character_episodes/{characterId}", icon = null){
        fun createRoute(characterId: Int): String {
            return "character_episodes/$characterId"
        }
    }
    data object EpisodesDetails : Screens(title = "Episode Details", route = "episode_details/{episodeId}", icon = null){
        fun createRoute(episodeId: Int): String {
            return "episode_details/$episodeId"
        }
    }
}