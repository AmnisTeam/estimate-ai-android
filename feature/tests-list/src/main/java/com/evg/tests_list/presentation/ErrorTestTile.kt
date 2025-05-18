package com.evg.tests_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evg.utils.model.TestIcons
import com.evg.resource.R
import com.evg.tests_list.presentation.model.TestState
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme

@Composable
fun ErrorTestTile(
    errorTest: TestState.ErrorTest,
) {
    val paddings = 10.dp
    val cardBackground = AppTheme.colors.tileBackground
        .copy(red = (AppTheme.colors.tileBackground.red + 0.05f))

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(BorderRadius))
            .background(color = cardBackground)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = paddings)
                .height(80.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val icon = when(errorTest.icon) {
                TestIcons.ESSAY -> painterResource(id = R.drawable.essay)
                TestIcons.UNKNOWN -> painterResource(id = R.drawable.unknown)
            }
            Icon(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .size(50.dp),
                painter = icon,
                contentDescription = null,
                tint = AppTheme.colors.text,
            )

            Column(
                modifier = Modifier
                    .padding(paddings)
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = "${stringResource(R.string.error_test)} #${errorTest.id}",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickableRipple {

                    },
                imageVector = Icons.Filled.Refresh,
                contentDescription = null,
                tint = AppTheme.colors.text,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ErrorTestTilePreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            ErrorTestTile(
                errorTest = TestState.ErrorTest(
                    id = 1,
                    icon = TestIcons.ESSAY,
                    createdAt = 0,
                ),
            )
        }
    }
}