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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evg.model.TestIcons
import com.evg.tests_list.domain.model.LoadingTest
import com.evg.tests_list.presentation.model.TestState
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme

@Composable
fun LoadingTestTile(
    loadingTest: TestState.LoadingTest,
    onClick: () -> Unit,
) {
    val paddings = 10.dp

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(BorderRadius))
            .background(color = AppTheme.colors.textFieldBackground)
            .clickableRipple {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = paddings)
                .height(80.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val icon = when(loadingTest.loadingTest.icon) {
                TestIcons.ESSAY -> Icons.Filled.Face
            }
            Icon(
                modifier = Modifier
                    .padding(vertical = paddings)
                    .size(50.dp),
                imageVector = icon,
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
                    text = loadingTest.loadingTest.title,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.text,
                )
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = loadingTest.loadingTest.description,
                    style = AppTheme.typography.small,
                    color = AppTheme.colors.textField,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = AppTheme.colors.primary,
                )
                Text(
                    modifier = Modifier,
                    text = "${loadingTest.loadingTest.progress}%",
                    color = AppTheme.colors.text,
                    fontSize = 11.sp,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoadingTestTilePreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            LoadingTestTile(
                loadingTest = TestState.LoadingTest(
                    loadingTest = LoadingTest(
                        icon = TestIcons.ESSAY,
                        title = "Title name example",
                        description = "Write an essay on any topic. Your English level will be estimated based on it.",
                        progress = 59,
                    ),
                ),
                onClick = {},
            )
        }
    }
}