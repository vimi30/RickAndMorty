package org.example.rammultiplatform.components.character

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.network.models.domain.Character
import org.example.rammultiplatform.util.BrightYellow

@Composable
fun CharacterGridItem(
    modifier: Modifier,
    character: Character,
    onClick: () -> Unit
){
    Card(
        modifier = modifier
            .clip( shape = RoundedCornerShape(12.dp) ),
        onClick = { onClick() },
        colors = CardDefaults.cardColors(BrightYellow)
    ) {
        Column{
            Box{
                CharacterImageComponent(imageUrl = character.imageUrl)
                CharacterStatusCircle(status = character.status, modifier = Modifier.padding(start = 8.dp))
            }
            Text(
                textAlign = TextAlign.Center,
                text = character.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                lineHeight = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            )

        }
    }
}

