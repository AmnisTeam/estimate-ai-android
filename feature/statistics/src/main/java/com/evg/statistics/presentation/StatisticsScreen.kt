package com.evg.statistics.presentation

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.evg.resource.R
import com.evg.statistics.presentation.model.DateTile
import com.evg.statistics.presentation.mvi.StatisticsState
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPaddingTile
import com.evg.ui.theme.VerticalPadding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StatisticsScreen(
    navigation: NavHostController,
    state: StatisticsState,
    getAllStatistics: () -> Unit,
) {
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = HorizontalPaddingTile,
                vertical = VerticalPadding,
            ),
    ) {
        DateSelection(
            dates = listOf(
                DateTile(
                    dateResId = R.string.week,
                    date = DateTile.Dates.WEEK,
                ),
                DateTile(
                    dateResId = R.string.month,
                    date = DateTile.Dates.MONTH,
                ),
                DateTile(
                    dateResId = R.string.year,
                    date = DateTile.Dates.YEAR,
                ),
            ),
            onDateRangeSelected = {
                val first = it.first ?: return@DateSelection
                val second = it.second ?: return@DateSelection
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val startDateString = dateFormat.format(Date(first))
                val endDateString = dateFormat.format(Date(second))

                Toast.makeText(context, "$startDateString to $endDateString", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun StatisticsScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            StatisticsScreen(
                navigation = NavHostController(LocalContext.current),
                state = StatisticsState(
                    isStatisticsLoading = false,
                ),
                getAllStatistics = {},
            )
        }
    }
}