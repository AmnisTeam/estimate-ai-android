package com.evg.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavHostController
import com.evg.login.domain.model.User
import com.evg.login.presentation.mvi.LoginState
import com.evg.resource.R
import com.evg.ui.custom.AuthorizationButton
import com.evg.ui.custom.AuthorizationTextField
import com.evg.ui.extensions.clickableRipple
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.AuthorizationIconSize
import com.evg.ui.theme.AuthorizationSpaceBy
import com.evg.ui.theme.AuthorizationTextFieldSpaceBy
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme
import com.evg.ui.theme.HorizontalPadding
import com.evg.ui.theme.VerticalPadding
import com.evg.ui.theme.AuthorizationWelcomeTextSpaceBy

@Composable
fun LoginScreen(
    navigation: NavHostController,
    state: LoginState,
    loginUser: (User) -> Unit,
) {
    val isLoginLoading = state.isLoginLoading

    var emailText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("test@mail.com"))
    }
    var passwordText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("12345678"))
    }

    val welcomeText = stringResource(R.string.welcome)
    val loginAccountProgressText = stringResource(R.string.login_account_progress)
    val emailLabel = stringResource(R.string.email)
    val enterEmailPlaceholder = stringResource(R.string.enter_email)
    val passwordLabel = stringResource(R.string.password)
    val enterPasswordPlaceholder = stringResource(R.string.enter_password)
    val iDontHaveAccountText = stringResource(R.string.i_dont_have_account)
    val logInText = stringResource(R.string.log_in)
    val signUpText = stringResource(R.string.sign_up)
    val forgotPasswordText = stringResource(R.string.forgot_password)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = HorizontalPadding,
                vertical = VerticalPadding,
            )
            .verticalScroll(rememberScrollState()),
    ) {
        Text(modifier = Modifier.clickableRipple { navigation.navigate("tests") }, text = "Go To Main", color = AppTheme.colors.text)

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
                text = loginAccountProgressText,
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
        }

        Spacer(modifier = Modifier.height(AuthorizationTextFieldSpaceBy))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .clickableRipple {
                        navigation.navigate("password_reset") {
                            popUpTo("login") {
                                inclusive = true
                            }
                        }
                    },
                text = forgotPasswordText,
                style = AppTheme.typography.body,
                color = AppTheme.colors.primary,
            )
        }

        Spacer(modifier = Modifier.height(AuthorizationSpaceBy))
        Spacer(modifier = Modifier.weight(1f))

        AuthorizationButton(
            isLoading = isLoginLoading,
            onClick = {
                loginUser(User(email = emailText.text, password = passwordText.text))
            },
            buttonText = logInText,
        )

        Spacer(modifier = Modifier.height(AuthorizationTextFieldSpaceBy))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
                    .height(3.dp)
                    .clip(shape = RoundedCornerShape(BorderRadius))
                    .background(AppTheme.colors.textField)
            )
            Text(
                text = "or continue with",
                style = AppTheme.typography.body,
                color = AppTheme.colors.textFieldName,
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
                    .height(3.dp)
                    .clip(shape = RoundedCornerShape(BorderRadius))
                    .background(AppTheme.colors.textField)
            )
        }

        Spacer(modifier = Modifier.height(AuthorizationTextFieldSpaceBy))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AuthorizationTextFieldSpaceBy, Alignment.CenterHorizontally)
        ) {
            LoginCard(
                backgroundColor = Color.White,
                icon = painterResource(R.drawable.apple),
                onClick = {}
            )
            LoginCard(
                backgroundColor = null,
                icon = painterResource(R.drawable.google),
                onClick = {}
            )
            LoginCard(
                backgroundColor = null,
                icon = painterResource(R.drawable.discord),
                onClick = {}
            )
        }

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
                        navigation.navigate("registration") {
                            popUpTo("login") {
                                inclusive = true
                            }
                        }
                    },
                text = buildAnnotatedString {
                    this.append("$iDontHaveAccountText ")
                    withStyle(style = SpanStyle(color = AppTheme.colors.primary)) {
                        append(signUpText)
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
fun LoginScreenPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            LoginScreen(
                navigation = NavHostController(LocalContext.current),
                state = LoginState(
                    isLoginLoading = false,
                ),
                loginUser = {}
            )
        }
    }
}
