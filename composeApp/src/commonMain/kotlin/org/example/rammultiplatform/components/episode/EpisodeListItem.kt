package com.example.ram.components.episode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
//import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.network.models.domain.Episode
import org.example.rammultiplatform.util.BrightYellow
import org.jetbrains.compose.resources.painterResource
import rammultiplatform.composeapp.generated.resources.Res
import rammultiplatform.composeapp.generated.resources.round_play_icon

@Composable
fun EpisodeListItem( episode: Episode, onClicked: () -> Unit) {
    ElevatedCard(
        colors = CardDefaults.cardColors(BrightYellow),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp),
        onClick = { onClicked() },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Icon(painter = painterResource(Res.drawable.round_play_icon), contentDescription = "Play Icon")
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Text(
                    text = "Episode ${episode.episodeNumber}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = episode.name,
                    fontSize = 20.sp,
                )
                Text(
                    text = episode.airDate,
                    fontSize = 12.sp,
                )
            }
        }

    }
}


//@Preview
//@Composable
//fun EpisodeListItemPreview() {
//    RAMTheme {
//        EpisodeListItem(episode = episode) {}
//    }
//}
//
//val episode = Episode (
//    name = "The Ricklantis Mixup",
//    airDate = "September 10, 2017",
//    id = 28,
//    episodeNumber = 7,
//    characterIdsInEpisode = emptyList(),
//    seasonNumber = 3
//)