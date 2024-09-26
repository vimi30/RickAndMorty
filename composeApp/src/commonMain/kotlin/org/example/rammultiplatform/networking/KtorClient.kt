package org.example.rammultiplatform.networking

import com.example.network.models.domain.Character
import com.example.network.models.domain.CharacterGender
import com.example.network.models.domain.CharacterPage
import com.example.network.models.domain.CharacterStatus
import com.example.network.models.domain.Episode
import com.example.network.models.domain.EpisodePage
import com.example.network.models.remote.RemoteCharacter
import com.example.network.models.remote.RemoteCharacterPage
import com.example.network.models.remote.RemoteEpisode
import com.example.network.models.remote.RemoteEpisodePage
import com.example.network.models.remote.toDomainCharacter
import com.example.network.models.remote.toDomainCharacterPage
import com.example.network.models.remote.toDomainEpisode
import com.example.network.models.remote.toDomainEpisodePage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException
import org.example.rammultiplatform.util.NetworkError
import org.example.rammultiplatform.util.Result

class KtorClient(
    private val client: HttpClient
) {

    suspend fun getCharacter(id: Int): Result<Character, NetworkError> {
        return safeNetworkCall(
            networkCall = { client.get("api/character/$id") },
            successHandler = { response ->
                response.body<RemoteCharacter>()
                    .toDomainCharacter()
            }
        )
    }

    suspend fun getCharacters(characterIds: List<Int>): Result<List<Character>, NetworkError> {
        val characterIdsCommaSeparated = characterIds.joinToString(separator = ",")
        return safeNetworkCall(
            networkCall = { client.get("api/character/$characterIdsCommaSeparated") },
            successHandler = { response ->
                response.body<List<RemoteCharacter>>().map { it.toDomainCharacter() }
            }
        )
    }

    suspend fun getCharacterByPage(pageNumber: Int): Result<CharacterPage, NetworkError> {

        return safeNetworkCall(
            networkCall = { client.get("api/character/?page=$pageNumber") },
            successHandler = { response ->
                response.body<RemoteCharacterPage>().toDomainCharacterPage()
            }
        )
    }

    suspend fun searchCharacter(
        name: String?,
        status: CharacterStatus?,
        gender: CharacterGender?
    ): Result<CharacterPage, NetworkError> {
        return safeNetworkCall(
            networkCall = { client.get("api/character/?name=${name ?: ""}&status=${status?.displayName ?: ""}&gender=${gender?.displayName ?: ""}") },
            successHandler = { response ->
                response.body<RemoteCharacterPage>()
                    .toDomainCharacterPage()
            }
        )
    }

    suspend fun getEpisode(episodeId: Int): Result<Episode, NetworkError> {
        return safeNetworkCall(
            networkCall = { client.get("api/episode/$episodeId") },
            successHandler = { response ->
                response.body<RemoteEpisode>()
                    .toDomainEpisode()
            }
        )
    }

    suspend fun getEpisodesByPage(pageNumber: Int): Result<EpisodePage, NetworkError> {
        return safeNetworkCall(
            networkCall = { client.get("api/episode/?page=$pageNumber") },
            successHandler = { response ->
                response.body<RemoteEpisodePage>()
                    .toDomainEpisodePage()
            }
        )
    }

    suspend fun getEpisodes(episodes: List<Int>): Result<List<Episode>, NetworkError> {
//        return if (episodes.size == 1) {
//            getEpisode(episodes[0]).mapSuccess {
//                listOf(it)
//            }
//        } else {
            val idsCommaSeparated = episodes.joinToString(separator = ",")
            return safeNetworkCall(
                networkCall = { client.get("api/episode/$idsCommaSeparated") },
                successHandler = { response ->
                    response.body<List<RemoteEpisode>>()
                        .map { it.toDomainEpisode() }
                }
            )
//        }
    }

    private inline fun <D> safeNetworkCall(
        networkCall: () -> HttpResponse,
        successHandler: (HttpResponse) -> D
    ): Result<D, NetworkError> {
        val response = try {
            networkCall()
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: IOException) {
            return Result.Error(NetworkError.NO_INTERNET)
        }
        return when (response.status.value) {
            in 200..299 -> {
                try {
                    val data = successHandler(response)
                    Result.Success(data)
                } catch (e: SerializationException) {
                    Result.Error(NetworkError.SERIALIZATION)
                } catch (e: JsonConvertException){
                    Result.Error(NetworkError.SERIALIZATION)
                }
            }

            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            404 -> Result.Error(NetworkError.NOT_FOUND)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..509 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)

        }
    }
}
