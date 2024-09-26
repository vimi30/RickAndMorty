package com.example.network.models.domain

data class Episode(
    val airDate: String,
    val id: Int,
    val name: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val characterIdsInEpisode: List<Int>
)
