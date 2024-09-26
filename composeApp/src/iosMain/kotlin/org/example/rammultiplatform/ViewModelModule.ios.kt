package org.example.rammultiplatform

import org.example.rammultiplatform.viewmodels.CharacterDetailsViewModel
import org.example.rammultiplatform.viewmodels.HomeScreenViewModel
import org.example.rammultiplatform.viewmodels.CharacterEpisodeViewModel
import org.example.rammultiplatform.viewmodels.EpisodeDetailsViewModel
import org.example.rammultiplatform.viewmodels.EpisodesViewModel
import org.example.rammultiplatform.viewmodels.CharacterSearchViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::HomeScreenViewModel)
    singleOf(::CharacterDetailsViewModel)
    singleOf(::CharacterEpisodeViewModel)
    singleOf(::CharacterSearchViewModel)
    singleOf(::EpisodesViewModel)
    singleOf(::EpisodeDetailsViewModel)
}