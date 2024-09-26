package org.example.rammultiplatform.repository

import com.example.network.models.domain.Episode
import com.example.network.models.domain.EpisodePage
import org.example.rammultiplatform.networking.KtorClient
import org.example.rammultiplatform.util.NetworkError
import org.example.rammultiplatform.util.Result

class EpisodesRepository(private val apiClient: KtorClient) {

    suspend fun fetchEpisodePage(pageNumber: Int): Result<EpisodePage, NetworkError> {
        return apiClient.getEpisodesByPage(pageNumber)
    }

    suspend fun fetchEpisode(episodeId: Int): Result<Episode, NetworkError> {
        return apiClient.getEpisode(episodeId)
    }

    suspend fun fetchEpisodesByIds(episodeIds: List<Int>): Result<List<Episode>, NetworkError> {
        return apiClient.getEpisodes(episodeIds)
    }

}