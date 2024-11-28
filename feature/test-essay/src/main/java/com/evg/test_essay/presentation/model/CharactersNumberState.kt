package com.evg.test_essay.presentation.model

import androidx.compose.ui.graphics.Color

enum class CharactersNumberState(val color: Color) {
    NOT_ENOUGH(com.evg.ui.theme.NOT_ENOUGH),
    NORMAL(com.evg.ui.theme.NORMAL),
    ENOUGH(com.evg.ui.theme.ENOUGH),
    MAXIMUM(com.evg.ui.theme.NOT_ENOUGH)
}