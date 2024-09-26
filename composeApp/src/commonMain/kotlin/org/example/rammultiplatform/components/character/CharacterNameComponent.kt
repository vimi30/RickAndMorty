package org.example.rammultiplatform.components.character

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.example.rammultiplatform.util.RichPurple
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun CharacterNameComponent( name: String ) {
    Text(
        text = name,
        fontSize = 42.sp,
        lineHeight = 42.sp,
        fontWeight = FontWeight.Bold,
        color = RichPurple
    )
}

@Preview
@Composable
private fun CharacterNameComponentPreview() {
    CharacterNameComponent(name = "Coach Feratu (Balik Alistane)")
}