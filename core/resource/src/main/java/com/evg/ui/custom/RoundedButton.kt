package com.evg.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.resource.R
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    icon: Painter,
    iconColor: Color,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .size(50.dp)
            .clickableRipple {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(30.dp),
                color = AppTheme.colors.primary,
            )
        } else {
            Icon(
                modifier = Modifier
                    .size(20.dp),
                painter = icon,
                contentDescription = null,
                tint = iconColor,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TestsListScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            RoundedButton(
                backgroundColor = Color(0xFF2B2930),
                icon = painterResource(id = R.drawable.plus),
                iconColor = AppTheme.colors.primary,
                onClick = {},
            )
        }
    }
}