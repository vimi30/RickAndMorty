package org.example.rammultiplatform.components.character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.network.models.domain.Character
import com.example.network.models.domain.CharacterGender
import com.example.network.models.domain.CharacterStatus
import org.example.rammultiplatform.components.commons.DataPoint
import org.example.rammultiplatform.components.commons.DataPointComponent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharacterListItem(
    modifier: Modifier = Modifier,
    character: Character,
    characterDatePoints: List<DataPoint>,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(140.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Box {
            CharacterImageComponent(
                imageUrl = character.imageUrl, modifier =
                Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )
            CharacterStatusCircle(status = character.status)
        }

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            content = {
                items(
                    items = listOf(
                        DataPoint(
                            title = "Name",
                            description = character.name
                        )
                    ) + characterDatePoints,
                    key = { it.hashCode() }
                ) { dataPoint ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        DataPointComponent(datapoint = formatDataPoint(dataPoint))
                    }
                }
            }
        )
    }
}

private fun formatDataPoint(dataPoint: DataPoint): DataPoint {
    val newDescription = if (dataPoint.description.length > 14) {
        dataPoint.description.take(12) + ".."
    } else {
        dataPoint.description
    }

    return dataPoint.copy(description = newDescription)
}

@Preview
@Composable
fun CharacterListItemPreview() {
    CharacterListItem(
        character = Character(
            name = "Rick",
            created = "asdfk",
            location = Character.Location(
                name = "Location",
                url = "Location"
            ),
            origin = Character.Origin(
                name = "Origin",
                url = "Origin"
            ),
            gender = CharacterGender.Male,
            imageUrl = "",
            species = "",
            url = "",
            type = "",
            episodeIds = emptyList(),
            id = 0,
            status = CharacterStatus.Dead
        ),
        characterDatePoints = emptyList()
    ) { }
}