package org.example.rammultiplatform.components.commons

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.example.rammultiplatform.util.NavyBlue
import org.example.rammultiplatform.util.RichPurple

data class DataPoint(
    val title: String,
    val description: String
)


@Composable
fun DataPointComponent(datapoint: DataPoint) {
    Column {
        Text(
            text = datapoint.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = RichPurple
        )

        Text(
            text = datapoint.description,
            fontSize = 24.sp,
            color = NavyBlue
        )
    }
}