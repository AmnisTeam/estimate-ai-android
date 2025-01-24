package com.evg.statistics.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.resource.R
import com.evg.ui.theme.EstimateAITheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InfoGridScreen(tiles: List<Pair<String, String?>>) {
    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        maxItemsInEachRow = Int.MAX_VALUE,
        maxLines = Int.MAX_VALUE,
        overflow = FlowRowOverflow.Clip
    ) {
        tiles.forEach { tile ->
            val data = tile.second ?: stringResource(R.string.no_data)
            InfoTile(title = tile.first, info = data)
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun InfoGridScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        InfoGridScreen(
            tiles = listOf(
                Pair("Frequent day","Friday"),
                Pair("Frequent day","Friday"),
                Pair("Frequent day","Friday"),
                Pair("Frequent day","Friday"),
            )
        )
    }
}
