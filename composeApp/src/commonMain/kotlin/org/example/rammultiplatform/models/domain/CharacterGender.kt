package com.example.network.models.domain

sealed class CharacterGender(val displayName: String) {
    object Male: CharacterGender("Male")
    object Female: CharacterGender("Female")
    object GenderLess: CharacterGender("No Gender")
    object Unknown: CharacterGender("Not Specified")
}