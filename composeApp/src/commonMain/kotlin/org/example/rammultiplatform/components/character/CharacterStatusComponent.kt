package org.example.rammultiplatform.components.character

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.network.models.domain.CharacterStatus

@Composable
fun CharacterStatusComponent(characterStatus: CharacterStatus) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .border(width = 2.dp, color = characterStatus.color, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(text = "Status: ", fontSize = 14.sp)
        Text(text = characterStatus.displayName, fontSize = 20.sp, fontWeight = FontWeight.Bold)

    }
}

//@Preview
//@Composable
//fun CharacterStatusComponentPreviewAlive() {
//    RAMTheme {
//        CharacterStatusComponent(characterStatus = CharacterStatus.Alive)
//    }
//}
//
//@Preview
//@Composable
//fun CharacterStatusComponentPreviewDead() {
//    RAMTheme {
//        CharacterStatusComponent(characterStatus = CharacterStatus.Dead)
//    }
//}
//
//@Preview
//@Composable
//fun CharacterStatusComponentPreviewUnknown() {
//    RAMTheme {
//        CharacterStatusComponent(characterStatus = CharacterStatus.Unknown)
//    }
//}