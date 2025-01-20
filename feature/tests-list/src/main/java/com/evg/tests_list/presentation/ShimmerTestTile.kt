package com.evg.tests_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evg.utils.model.TestIcons
import com.evg.utils.model.TestLevelColors
import com.evg.resource.R
import com.evg.tests_list.presentation.model.TestState
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerTestTile() {
    val paddings = 10.dp

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(BorderRadius))
            .background(color = AppTheme.colors.textFieldBackground)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = paddings)
                .height(80.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(
                modifier = Modifier.shimmer(),
            ) {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(shape = RoundedCornerShape(BorderRadius))
                        .background(AppTheme.colors.shimmer)
                )
            }

            Column(
                modifier = Modifier
                    .padding(paddings)
                    .weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .shimmer(),
                ) {
                    Box(
                        modifier = Modifier
                            .height(13.dp)
                            .width(150.dp)
                            .clip(shape = RoundedCornerShape(BorderRadius))
                            .background(AppTheme.colors.shimmer)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .shimmer(),
                ) {
                    Box(
                        modifier = Modifier
                            .height(11.dp)
                            .width(250.dp)
                            .clip(shape = RoundedCornerShape(BorderRadius))
                            .background(AppTheme.colors.shimmer)
                    )
                }
            }

            Box(
                modifier = Modifier.shimmer(),
            ) {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(shape = RoundedCornerShape(BorderRadius))
                        .background(AppTheme.colors.shimmer)
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ShimmerTestTilePreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            Column {
                FinishedTestTilePreview(darkTheme = darkTheme)
                ShimmerTestTile()
            }
        }
    }
}