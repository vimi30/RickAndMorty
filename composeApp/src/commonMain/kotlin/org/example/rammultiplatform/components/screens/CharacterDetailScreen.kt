package org.example.rammultiplatform.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
//import coil.compose.SubcomposeAsyncImage
import org.example.rammultiplatform.components.character.CharacterDetailsNamePlateComponent
import org.example.rammultiplatform.components.commons.DataPointComponent
import org.example.rammultiplatform.components.commons.ErrorComponent
import org.example.rammultiplatform.components.commons.LoadingState
import org.example.rammultiplatform.components.commons.SimpleToolBar
import org.example.rammultiplatform.components.commons.UiState
import kotlinx.coroutines.flow.StateFlow
import org.example.rammultiplatform.util.PortalGreen
import org.example.rammultiplatform.viewmodels.CharacterDetailsState

@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    screenState: StateFlow<CharacterDetailsState>,
    onBackClicked: () -> Unit,
    onEpisodeClicked: (Int) -> Unit,
    fetchCharacter: (Int) -> Unit
) {

    val characterViewState by screenState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        fetchCharacter(characterId)
    })

    Column (
        modifier = Modifier.fillMaxSize().background(color = PortalGreen)
    ){
        SimpleToolBar(
            title = "Character Details",
            onBackAction = { onBackClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(all = 16.dp),
        ) {
            when (val viewState = characterViewState) {
                is UiState.Error -> {
                    item {
                        ErrorComponent(viewState.errorMessage)
                    }
                }

                UiState.Loading -> item { LoadingState() }
                is UiState.Success -> {
                    //Name Plate
                    item {
                        CharacterDetailsNamePlateComponent(
                            name = viewState.data.character.name,
                            status = viewState.data.character.status
                        )
                    }

                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    //Image
                    item {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp)),
                            model = viewState.data.character.imageUrl,
                            contentDescription = "Character Image",
                            loading = { LoadingState() }
                        )
                    }

                    //Data Point
                    items(viewState.data.characterDataPoint) { dataPoint ->
                        Spacer(modifier = Modifier.height(32.dp))
                        DataPointComponent(datapoint = dataPoint)
                    }
                    item { Spacer(modifier = Modifier.height(32.dp)) }

                    //Button
                    item{
                        Row (
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(
                                modifier = Modifier
                                    .border(
                                        width = 2.dp,
                                        color = Color(0xff004d40),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { onEpisodeClicked(characterId) }
                                    .padding(8.dp),
                                text = "View all episodes",
                                color = Color(0xff004d40),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,

                                )
                        }
                    }
                    item { Spacer(modifier = Modifier.height(64.dp)) }
                }
            }


        }
    }
}