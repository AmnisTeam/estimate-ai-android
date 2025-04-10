package com.evg.estimateai

import android.Manifest
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.evg.estimateai.mapper.toAppStyle
import com.evg.estimateai.mapper.toIsDarkMode
import com.evg.shared_prefs.domain.repository.SharedPrefsRepository
import com.evg.ui.theme.AppSize
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.EstimateAITheme
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPrefsRepository: SharedPrefsRepository by inject()
        val initialTheme = sharedPrefsRepository.getAppTheme().toIsDarkMode()
        AppCompatDelegate.setDefaultNightMode(initialTheme)

        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0,
            )
        }

        val initialStyle = sharedPrefsRepository.getAppStyle().toAppStyle()
        val isUserAuthenticated = sharedPrefsRepository.getUserToken() != null

        enableEdgeToEdge()
        setContent {
            AppTheme.apply {
                style = initialStyle
                nightMode = initialTheme
                textSize = AppSize.Medium
            }

            EstimateAITheme {
                MainScreen(
                    isUserAuthenticated = isUserAuthenticated,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun MainActivityPreview() {
    EstimateAITheme {
        MainScreen(
            isUserAuthenticated = false,
        )
    }
}