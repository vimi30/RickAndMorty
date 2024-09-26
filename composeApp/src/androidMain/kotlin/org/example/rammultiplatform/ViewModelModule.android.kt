package org.example.rammultiplatform

import org.example.rammultiplatform.viewmodels.CharacterDetailsViewModel
import org.example.rammultiplatform.viewmodels.HomeScreenViewModel
import org.example.rammultiplatform.viewmodels.CharacterEpisodeViewModel
import org.example.rammultiplatform.viewmodels.EpisodeDetailsViewModel
import org.example.rammultiplatform.viewmodels.EpisodesViewModel
import org.example.rammultiplatform.viewmodels.CharacterSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::CharacterDetailsViewModel)
    viewModelOf(::CharacterEpisodeViewModel)
    viewModelOf(::EpisodeDetailsViewModel)
    viewModelOf(::EpisodesViewModel)
    viewModelOf(::CharacterSearchViewModel)

}