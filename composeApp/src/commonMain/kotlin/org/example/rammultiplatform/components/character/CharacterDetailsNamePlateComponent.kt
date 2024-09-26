package org.example.rammultiplatform.components.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.network.models.domain.CharacterStatus

@Composable
fun CharacterDetailsNamePlateComponent(
    name: String,
    status: CharacterStatus
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        CharacterNameComponent(name)
        Spacer( modifier = Modifier.height(4.dp))
        CharacterStatusComponent(characterStatus = status)
    }
}