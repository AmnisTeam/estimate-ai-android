package com.evg.ui.custom

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults.DecorationBox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evg.resource.R
import com.evg.ui.theme.AppTheme
import com.evg.ui.theme.BorderRadius
import com.evg.ui.theme.EstimateAITheme
import java.lang.Error

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationTextField(
    filedName: String,
    placeholder: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    //isError: Boolean = false,
    isPassword: Boolean = false,
) {
    var passwordVisible by remember { mutableStateOf(!isPassword) }
    val icon = if (passwordVisible) painterResource(R.drawable.eye_off) else painterResource(R.drawable.eye_on)

    Column {
        Text(
            text = filedName,
            color = AppTheme.colors.textFieldName,
            style = AppTheme.typography.body,
        )

        Spacer(modifier = Modifier.height(5.dp))

        BasicTextField(
            value = value,
            onValueChange = { newText -> onValueChange(newText) },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .clip(RoundedCornerShape(BorderRadius))
                /*.then(
                    if (isError) {
                        Modifier.border(1.dp, color = Color.Red, shape = RoundedCornerShape(BorderRadius))
                    } else { Modifier }
                )*/,
            textStyle = AppTheme.typography.body.copy(
                color = AppTheme.colors.text
            ),
            cursorBrush = SolidColor(AppTheme.colors.primary),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            decorationBox =
            @Composable { innerTextField ->
                DecorationBox(
                    value = value.text,
                    contentPadding = PaddingValues(horizontal = 15.dp, vertical = 5.dp),
                    visualTransformation = VisualTransformation.None,
                    innerTextField = { innerTextField() },
                    placeholder = {
                        Text(
                            text = placeholder,
                            color = AppTheme.colors.textField,
                            style = AppTheme.typography.body,
                        )
                    },
                    trailingIcon = {
                        if (isPassword) {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .weight(1f),
                                    painter = icon,
                                    contentDescription = null,
                                    tint = AppTheme.colors.textField
                                )
                            }
                        }
                    },
                    singleLine = true,
                    enabled = true,
                    //isError = isError,
                    interactionSource = remember { MutableInteractionSource() },
                    colors = TextFieldDefaults.colors().copy(
                        focusedContainerColor = AppTheme.colors.textFieldBackground,
                        unfocusedContainerColor = AppTheme.colors.textFieldBackground,
                        errorContainerColor = AppTheme.colors.textFieldBackground,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }
        )
    }
}


@Composable
@Preview(showBackground = true)
fun AuthorizationTextFieldPreview(darkTheme: Boolean = true) {
    EstimateAITheme(darkTheme = darkTheme) {
        Surface(color = AppTheme.colors.background) {
            AuthorizationTextField(
                filedName = "Email",
                placeholder = "Enter your email",
                value = TextFieldValue(text = "test.email@mail.com"),
                onValueChange = {},
                //isError = true,
                isPassword = true,
            )
        }
    }
}
