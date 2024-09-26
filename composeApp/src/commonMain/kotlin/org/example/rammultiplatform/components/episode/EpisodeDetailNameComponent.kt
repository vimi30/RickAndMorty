package org.example.rammultiplatform.components.episode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.network.models.domain.Episode
import org.example.rammultiplatform.util.RichPurple

@Composable
fun EpisodeDetailNameComponent(episode: Episode) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = episode.name,
            fontSize = 36.sp,
            lineHeight = 36.sp,
            fontWeight = FontWeight.Bold,
            color = RichPurple,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Season ${episode.seasonNumber}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = RichPurple,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text =  "Aired On: ${episode.airDate}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = RichPurple,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}