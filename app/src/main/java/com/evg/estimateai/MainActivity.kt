package com.evg.estimateai

import android.Manifest
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.AppSize
import com.evg.ui.theme.AppStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0,
            )
        }

        enableEdgeToEdge()
        setContent {
            val currentStyle = remember { mutableStateOf(AppStyle.Purple) }
            val currentFontSize = remember { mutableStateOf(AppSize.Medium) }

            EstimateAITheme(
                style = currentStyle.value,
                textSize = currentFontSize.value,
            ) {
                MainScreen()
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun MainActivityPreview() {
    EstimateAITheme {
        MainScreen()
    }
}