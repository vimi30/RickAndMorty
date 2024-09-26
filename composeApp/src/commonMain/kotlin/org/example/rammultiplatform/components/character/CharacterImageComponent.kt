package org.example.rammultiplatform.components.character

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import org.example.rammultiplatform.components.commons.LoadingState

private val defaultModifier = Modifier
    .fillMaxWidth()
    .aspectRatio(1f)
    .clip(RoundedCornerShape(12.dp))

@Composable
fun CharacterImageComponent(imageUrl: String, modifier: Modifier = defaultModifier) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = "Character Image",
        modifier = modifier,
        loading = { LoadingState( modifier =  Modifier.size(20.dp)) }
    )
}