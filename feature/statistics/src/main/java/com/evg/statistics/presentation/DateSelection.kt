package com.evg.statistics.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import androidx.compose.ui.unit.dp
import com.evg.resource.R
import com.evg.statistics.presentation.model.DateTile
import com.evg.statistics.presentation.mvi.StatisticsViewModel
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.extensions.lighten
import com.evg.ui.theme.BorderRadius


@Composable
fun DateSelection(
    dates: List<DateTile>,
    onDateRangeSelected: (Pair<Long, Long>) -> Unit,
) {
    var selected: DateTile.Dates? by rememberSaveable { mutableStateOf(StatisticsViewModel.defaultSelect) }
    var showDateRangePicker by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape((BorderRadius - 5.dp).coerceAtLeast(0.dp)))
                .background(AppTheme.colors.textFieldBackground),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        ) {
            dates.forEach { dateTile ->
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 5.dp, horizontal = 2.dp)
                        .clip(RoundedCornerShape(BorderRadius))
                        .clickableRipple {
                            selected = dateTile.date
                            val range = dateTile.date.toTimeRange()
                            onDateRangeSelected(range.first to range.second)
                        }
                        .then(
                            if (selected == dateTile.date) {
                                Modifier
                                    .background(AppTheme.colors.textFieldBackground.lighten(0.07f)) //TODO check on light theme
                            } else {
                                Modifier
                            }
                        )
                        .padding(10.dp),
                    text = stringResource(dateTile.dateResId),
                    color = AppTheme.colors.text,
                    style = AppTheme.typography.body,
                    textAlign = TextAlign.Center,
                )
            }
        }

        Icon(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(23.dp)
                .align(Alignment.CenterVertically)
                .clickableRipple {
                    showDateRangePicker = true
                },
            painter = painterResource(R.drawable.calendar),
            contentDescription = null, //TODO edit contentDescription
            tint = AppTheme.colors.text,
        )
    }



    if (showDateRangePicker) {
        DateRangePickerModal(
            onDateRangeSelected = { dateRange ->
                val range = Pair(dateRange.first ?: return@DateRangePickerModal, dateRange.second?: return@DateRangePickerModal)
                showDateRangePicker = false
                onDateRangeSelected(range)
                selected = null
            },
            onDismiss = { showDateRangePicker = false }
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DateSelectionPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
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
                onDateRangeSelected = { }
            )
        }
    }
}