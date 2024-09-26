package org.example.rammultiplatform.components.commons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.network.models.domain.Episode
import org.example.rammultiplatform.util.RichPurple

@Composable
fun EpisodeRowComponent(episode: Episode) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        DataPointComponent(
            datapoint = DataPoint(
                title = "Episode",
                description = episode.episodeNumber.toString()
            )
        )

        Spacer( modifier = Modifier.width(64.dp) )

        Column {
            Text(
                text = episode.name,
                fontSize = 24.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                color = RichPurple
            )

            Text(
                text = episode.airDate,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                color = RichPurple
            )
        }
    }
}