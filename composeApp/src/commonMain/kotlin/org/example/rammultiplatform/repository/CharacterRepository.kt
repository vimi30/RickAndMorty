package org.example.rammultiplatform.repository

import com.example.network.models.domain.Character
import com.example.network.models.domain.CharacterGender
import com.example.network.models.domain.CharacterPage
import com.example.network.models.domain.CharacterStatus
import org.example.rammultiplatform.networking.KtorClient
import org.example.rammultiplatform.util.NetworkError
import org.example.rammultiplatform.util.Result

class CharacterRepository(
    private val apiClient: KtorClient
) {

    suspend fun fetchCharacterPage(pageNumber: Int): Result<CharacterPage, NetworkError> {
        return apiClient.getCharacterByPage(pageNumber)
    }

    suspend fun fetchCharacter(characterId: Int): Result<Character, NetworkError> {
        return apiClient.getCharacter(characterId)
    }

    suspend fun fetchCharactersByIds(characterIds: List<Int>): Result<List<Character>, NetworkError> {
        return apiClient.getCharacters(characterIds)
    }

    suspend fun searchCharacter(
        name: String?,
        status: CharacterStatus?,
        gender: CharacterGender?
    ): Result<CharacterPage, NetworkError> {
        return apiClient.searchCharacter(name, status, gender)
    }
}