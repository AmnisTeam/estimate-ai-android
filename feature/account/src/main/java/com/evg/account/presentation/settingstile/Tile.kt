package com.evg.account.presentation.settingstile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.account.domain.model.AppLanguage
import com.evg.resource.R
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme

@Composable
fun <T : Enum<T>> Tile(
    title: String,
    initialValue: T,
    options: List<T>,
    optionStringRes: (T) -> Int,
    onOptionSelected: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var currentValue by remember { mutableStateOf(initialValue) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(0.dp))
                .clickableRipple { expanded = true }
                .padding(15.dp)
                .height(35.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = AppTheme.typography.body,
                color = AppTheme.colors.text,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                modifier = Modifier.width(90.dp),
                text = stringResource(optionStringRes(currentValue)),
                style = AppTheme.typography.body,
                color = AppTheme.colors.text,
                textAlign = TextAlign.Right,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                modifier = Modifier.size(10.dp),
                painter = painterResource(id = R.drawable.arrow_back2),
                contentDescription = null,
                tint = AppTheme.colors.text,
            )
        }

        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            containerColor = AppTheme.colors.tileBackground,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(optionStringRes(option)),
                            color = AppTheme.colors.text,
                            style = AppTheme.typography.body,
                        )
                    },
                    onClick = {
                        if (currentValue != option) {
                            currentValue = option
                            onOptionSelected(option)
                        }
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TextTilePreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            Tile(
                title = "App language",
                initialValue = AppLanguage.ENGLISH,
                options = AppLanguage.entries,
                optionStringRes = { it.labelRes },
                onOptionSelected = {},
            )
        }
    }
}