package com.evg.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme

@Composable
fun LoginCard(
    backgroundColor: Color? = null,
    icon: Painter,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(shape = RoundedCornerShape(BorderRadius))
            .then(
                if (backgroundColor == null) {
                    Modifier
                        .border(
                            width = 1.dp,
                            color = AppTheme.colors.textField,
                            shape = RoundedCornerShape(BorderRadius)
                        )
                } else {
                    Modifier.background(backgroundColor)
                }
            )
            .clickableRipple {
                onClick()
            }
    ) {
        Icon(
            modifier = Modifier
                .padding(10.dp),
            painter = icon,
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoginCardPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            LoginCard(
                icon = painterResource(R.drawable.google),
                onClick = {

                }
            )
        }
    }
}
