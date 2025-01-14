package com.evg.test_select.presentation

import android.content.res.Configuration
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.model.TestIcons
import com.evg.resource.R
import com.evg.test_select.presentation.model.TestType
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme

@Composable
fun TestTypeTile(
    testType: TestType,
    onClick: () -> Unit,
) {
    val paddings = 20.dp

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
                .height(150.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val icon = when (testType.icon) {
                TestIcons.ESSAY -> painterResource(id = R.drawable.essay)
                TestIcons.UNKNOWN -> painterResource(id = R.drawable.unknown)
            }
            Icon(
                modifier = Modifier
                    .padding(vertical = paddings)
                    .size(70.dp),
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
                    text = testType.title,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.text,
                )
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = testType.description,
                    style = AppTheme.typography.small,
                    color = AppTheme.colors.textField,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TestTypeTilePreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            TestTypeTile(
                testType = TestType(
                    icon = TestIcons.ESSAY,
                    title = "The essay test",
                    description = "Write an essay on any topic. Your English level will be estimated based on it",
                ),
                onClick = {}
            )
        }
    }
}