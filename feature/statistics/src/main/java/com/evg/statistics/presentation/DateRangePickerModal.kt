package com.evg.statistics.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.resource.R
import com.evg.ui.extensions.invert
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()
    val dateFormatter = remember { DatePickerDefaults.dateFormatter() }

    DatePickerDialog(
        colors = DatePickerDefaults.colors().copy(
            containerColor = AppTheme.colors.tileBackground,
        ),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis, // ?.plus(86399000L)
                        )
                    )
                    onDismiss()
                }
            ) {
                Text(
                    text = stringResource(R.string.apply),
                    color = AppTheme.colors.text,
                    style = AppTheme.typography.body,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = AppTheme.colors.text,
                    style = AppTheme.typography.body,
                )
            }
        }
    ) {
        DateRangePicker(
            colors = DatePickerDefaults.colors().copy(
                containerColor = AppTheme.colors.tileBackground,
                titleContentColor = AppTheme.colors.text,
                headlineContentColor = AppTheme.colors.text,
                weekdayContentColor = AppTheme.colors.text,
                subheadContentColor = AppTheme.colors.text,
                /*navigationContentColor = Color.Red,
                yearContentColor = Color.Red,
                currentYearContentColor = Color.Red,
                selectedYearContentColor = Color.Red,
                selectedYearContainerColor = Color.Red,*/
                dayContentColor = AppTheme.colors.text,
                selectedDayContentColor = AppTheme.colors.text.invert(),
                selectedDayContainerColor = AppTheme.colors.primary,
                todayContentColor = AppTheme.colors.primary,
                todayDateBorderColor = AppTheme.colors.primary,
                dayInSelectionRangeContainerColor = AppTheme.colors.primary.copy(alpha = 0.2f),
                dayInSelectionRangeContentColor = AppTheme.colors.text,
                dividerColor = AppTheme.colors.textFieldPlaceholder,
            ),
            state = dateRangePickerState,
            title = {
                /*Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = stringResource(R.string.select_date_range),
                    color = AppTheme.colors.text,
                    style = AppTheme.typography.body,
                )*/
            },
            headline = {
                val startDate = dateRangePickerState.selectedStartDateMillis?.let { dateFormatter.formatDate(
                    dateMillis = it,
                    locale = LocalConfiguration.current.locales[0],
                ) } ?: stringResource(R.string.start_date)
                val endDate = dateRangePickerState.selectedEndDateMillis?.let { dateFormatter.formatDate(
                    dateMillis = it,
                    locale = LocalConfiguration.current.locales[0],
                ) } ?: stringResource(R.string.end_date)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "$startDate – $endDate",
                        style = AppTheme.typography.body,
                        textAlign = TextAlign.Center,
                    )
                }
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DateRangePickerModalPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            DateRangePickerModal(
                onDateRangeSelected = { null to null },
                onDismiss = { },
            )
        }
    }
}