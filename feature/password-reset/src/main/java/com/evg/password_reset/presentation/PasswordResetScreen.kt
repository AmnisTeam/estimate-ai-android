package com.evg.password_reset.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.password_reset.domain.model.PasswordReset
import com.evg.password_reset.presentation.mvi.PasswordResetState
import com.evg.resource.R
import com.evg.ui.custom.AuthorizationButton
import com.evg.ui.custom.AuthorizationTextField
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.AuthorizationIconSize
import com.evg.ui.theme.AuthorizationSpaceBy
import com.evg.ui.theme.AuthorizationTextFieldSpaceBy
import com.evg.ui.theme.AuthorizationWelcomeTextSpaceBy
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.VerticalPadding

@Composable
fun PasswordResetScreen(
    state: PasswordResetState,
    modifier: Modifier = Modifier,
    onLoginScreen: () -> Unit,
    passwordReset: (PasswordReset) -> Unit,
) {
    val isEmailResetLoading = state.isEmailResetLoading

    var emailText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("test@mail.com"))
    }

    val passwordResetText = stringResource(R.string.password_reset)
    val emailLabel = stringResource(R.string.email)
    val enterEmailPlaceholder = stringResource(R.string.enter_email)
    val iAlreadyHaveAccountText = stringResource(R.string.i_already_have_account)
    val resetText = stringResource(R.string.reset)
    val logInText = stringResource(R.string.log_in)

    Column(
        modifier = modifier
            .padding(
                horizontal = HorizontalPadding,
                vertical = VerticalPadding,
            )
            .verticalScroll(rememberScrollState()),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AuthorizationWelcomeTextSpaceBy),
        ) {
            Icon(
                modifier = Modifier
                    .size(AuthorizationIconSize),
                painter = painterResource(R.drawable.eye_off),
                tint = AppTheme.colors.text,
                contentDescription = null,
            )
            Text(
                text = passwordResetText,
                style = AppTheme.typography.heading.copy(
                    fontWeight = FontWeight.Light,
                ),
                color = AppTheme.colors.text,
            )
        }

        Spacer(modifier = Modifier.height(AuthorizationSpaceBy))
        Spacer(modifier = Modifier.weight(0.3f))

        Column(
            verticalArrangement = Arrangement.spacedBy(AuthorizationTextFieldSpaceBy),
        ) {
            AuthorizationTextField(
                filedName = emailLabel,
                placeholder = enterEmailPlaceholder,
                value = emailText,
                onValueChange = { newText -> emailText = newText }
            )
        }

        Spacer(modifier = Modifier.height(AuthorizationSpaceBy))
        Spacer(modifier = Modifier.weight(1f))

        AuthorizationButton(
            isLoading = isEmailResetLoading,
            onClick = {
                passwordReset(PasswordReset(email = emailText.text))
            },
            buttonText = resetText,
        )

        Spacer(modifier = Modifier.height(AuthorizationSpaceBy))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .clickableRipple {
                        onLoginScreen()
                    },
                text = buildAnnotatedString {
                    this.append("$iAlreadyHaveAccountText ")
                    withStyle(style = SpanStyle(color = AppTheme.colors.primary)) {
                        append(logInText)
                    }
                },
                textAlign = TextAlign.Center,
                style = AppTheme.typography.body,
                color = AppTheme.colors.textFieldName,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PasswordResetScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            PasswordResetScreen(
                state = PasswordResetState(
                    isEmailResetLoading = false,
                ),
                onLoginScreen = {},
                passwordReset = {}
            )
        }
    }
}
