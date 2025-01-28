package com.evg.registration.presentation

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
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
import com.evg.registration.domain.model.User
import com.evg.registration.presentation.mvi.RegistrationState
import com.evg.resource.R
import com.evg.ui.custom.AuthorizationButton
import com.evg.ui.custom.AuthorizationTextField
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.snackbar.SnackBarController
import com.evg.ui.snackbar.SnackBarEvent
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.AuthorizationIconSize
import com.evg.ui.theme.AuthorizationSpaceBy
import com.evg.ui.theme.AuthorizationTextFieldSpaceBy
import com.evg.ui.theme.AuthorizationWelcomeTextSpaceBy
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.VerticalPadding
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    state: RegistrationState,
    modifier: Modifier = Modifier,
    onLoginScreen: () -> Unit,
    registrationUser: (User) -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isRegistrationLoading = state.isRegistrationLoading

    var emailText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("test@mail.com"))
    }
    var passwordText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("12345678"))
    }
    var passwordRepeatText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("12345678"))
    }

    val welcomeText = stringResource(R.string.welcome)
    val createAccountProgressText = stringResource(R.string.create_account_progress)
    val emailLabel = stringResource(R.string.email)
    val enterEmailPlaceholder = stringResource(R.string.enter_email)
    val passwordLabel = stringResource(R.string.password)
    val enterPasswordPlaceholder = stringResource(R.string.enter_password)
    val confirmPasswordLabel = stringResource(R.string.confirm_password)
    val enterPasswordAgainPlaceholder = stringResource(R.string.enter_password_again)
    val signUpText = stringResource(R.string.sign_up)
    val iAlreadyHaveAccountText = stringResource(R.string.i_already_have_account)
    val logInText = stringResource(R.string.log_in)

    val errorInvalidEmail = stringResource(R.string.error_invalid_email)
    val errorPasswordMismatch = stringResource(R.string.error_password_mismatch)
    val errorEmptyPassword = stringResource(R.string.error_empty_password)
    val errorPasswordMinLength = stringResource(R.string.error_password_min_length)
    val errorPasswordMaxLength = stringResource(R.string.error_password_max_length)

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
                text = welcomeText,
                style = AppTheme.typography.heading.copy(
                    fontWeight = FontWeight.Light,
                ),
                color = AppTheme.colors.text,
            )
            Text(
                text = createAccountProgressText,
                textAlign = TextAlign.Center,
                style = AppTheme.typography.body.copy(
                    fontWeight = FontWeight.Light,
                ),
                color = AppTheme.colors.text,
            )
        }

        Spacer(modifier = Modifier.height(AuthorizationSpaceBy))

        Column(
            verticalArrangement = Arrangement.spacedBy(AuthorizationTextFieldSpaceBy),
        ) {
            AuthorizationTextField(
                filedName = emailLabel,
                placeholder = enterEmailPlaceholder,
                value = emailText,
                onValueChange = { newText -> emailText = newText }
            )
            AuthorizationTextField(
                filedName = passwordLabel,
                placeholder = enterPasswordPlaceholder,
                value = passwordText,
                onValueChange = { newText -> passwordText = newText },
                isPassword = true,
            )
            AuthorizationTextField(
                filedName = confirmPasswordLabel,
                placeholder = enterPasswordAgainPlaceholder,
                value = passwordRepeatText,
                onValueChange = { newText -> passwordRepeatText = newText },
                isPassword = true,
            )
        }

        Spacer(modifier = Modifier.height(AuthorizationSpaceBy))
        Spacer(modifier = Modifier.weight(1f))

        AuthorizationButton(
            isLoading = isRegistrationLoading,
            onClick = {
                scope.launch {
                    if (!Patterns.EMAIL_ADDRESS.matcher(emailText.text).matches()) {
                        SnackBarController.sendEvent(event = SnackBarEvent(message = errorInvalidEmail))
                    } else if (passwordText.text != passwordRepeatText.text) {
                        SnackBarController.sendEvent(event = SnackBarEvent(message = errorPasswordMismatch))
                    } else if (passwordText.text.isEmpty()) {
                        SnackBarController.sendEvent(event = SnackBarEvent(message = errorEmptyPassword))
                    } else if (passwordText.text.length < 8) {
                        SnackBarController.sendEvent(event = SnackBarEvent(message = errorPasswordMinLength))
                    } else if (passwordText.text.length >= 24) {
                        SnackBarController.sendEvent(event = SnackBarEvent(message = errorPasswordMaxLength))
                    } else {
                        registrationUser(User(email = emailText.text, password = passwordText.text))
                    }
                }
            },
            buttonText = signUpText,
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
fun RegistrationScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            RegistrationScreen(
                state = RegistrationState(
                    isRegistrationLoading = false,
                ),
                onLoginScreen = {},
                registrationUser = {}
            )
        }
    }
}
