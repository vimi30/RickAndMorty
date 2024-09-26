package org.example.rammultiplatform.components.character

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.rammultiplatform.util.BrightYellow
import org.example.rammultiplatform.util.LightYellow

@Composable
fun SearchFilterChipComponent(
    selected: Boolean,
    label: String,
    onSelected: () -> Unit
) {
    ElevatedFilterChip(
        selected = selected,
        onClick = { onSelected() },
        label = { Text(text = label) },
        leadingIcon =
        if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Localized Description",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
        colors = FilterChipDefaults.filterChipColors(
            LightYellow,
            selectedContainerColor = BrightYellow
        )
    )
}

//@Preview
//@Composable
//fun SearchFilterChipComponentSelectedPreview(){
//    SearchFilterChipComponent(
//        true,
//        "Alive"
//    )
//}
//
//@Preview
//@Composable
//fun SearchFilterChipComponentNotSelectedPreview(){
//    SearchFilterChipComponent(
//        false,
//        "Alive"
//    )
//}