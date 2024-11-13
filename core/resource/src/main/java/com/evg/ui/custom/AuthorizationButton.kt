package com.evg.ui.custom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme

@Composable
fun AuthorizationButton(
    isLoading: Boolean,
    onClick: () -> Unit,
    buttonText: String,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        shape = RoundedCornerShape(BorderRadius),
        colors = ButtonColors(
            containerColor = AppTheme.colors.primary,
            contentColor = Color.Unspecified,
            disabledContainerColor = AppTheme.colors.primary.copy(alpha = 0.7f),
            disabledContentColor = AppTheme.colors.background.copy(alpha = 0.8f),
        ),
        enabled = !isLoading,
        onClick = { onClick() }
    ) {
        Text(
            text = buttonText,
            color = AppTheme.colors.background,
            style = AppTheme.typography.body.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}


@Composable
@Preview(showBackground = true)
fun AuthorizationButtonPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            AuthorizationButton(
                isLoading = false,
                onClick = {},
                buttonText = "Log In",
            )
        }
    }
}
