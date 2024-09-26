package org.example.rammultiplatform.components.commons


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.rammultiplatform.util.BrightYellow
import org.example.rammultiplatform.util.RichPurple
import org.jetbrains.compose.resources.painterResource
import rammultiplatform.composeapp.generated.resources.Res
import rammultiplatform.composeapp.generated.resources.arrow_back

private val defaultModifier = Modifier.fillMaxWidth()

@Composable
fun SimpleToolBar(
    title: String,
    onBackAction: (() -> Unit)? = null,
    modifier: Modifier = defaultModifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            BrightYellow
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 4.dp)
            ) {
                if (onBackAction != null) {
                    Box(modifier = Modifier
                        .background(shape = RoundedCornerShape(12.dp), color = Color.Transparent)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .clickable { onBackAction() }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.arrow_back),
                            contentDescription = "back arrow",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 24.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    color = RichPurple
                )
            }
        }
    }

}