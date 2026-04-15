package com.haykor.calendar.core.common.presentation.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haykor.calendar.R
import com.haykor.calendar.core.common.presentation.util.PasswordOutputTransformation
import com.haykor.calendar.core.ui.theme.AppTheme
import com.haykor.calendar.core.ui.theme.LocalSpacing

@Composable
fun AppOutlinedTextField(
    state: TextFieldState,
    label: String,
    modifier: Modifier = Modifier,
    outputTransformation: OutputTransformation? = null,
    isError: Boolean = false,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    caption: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    val spacing = LocalSpacing.current
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    val textFieldColors =
        OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            cursorColor = MaterialTheme.colorScheme.primary,
        )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing.extraSmall),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
        )

        BasicTextField(
            state = state,
            readOnly = readOnly,
            outputTransformation = outputTransformation,
            textStyle =
                MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = keyboardOptions,
            onKeyboardAction = onKeyboardAction,
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth(),
            decorator =
                OutlinedTextFieldDefaults.decorator(
                    state = state,
                    outputTransformation = outputTransformation,
                    interactionSource = interactionSource,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    isError = isError,
                    enabled = enabled,
                    colors = textFieldColors,
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.5.dp),
                    lineLimits = TextFieldLineLimits.SingleLine,
                    supportingText =
                        if (caption != null) {
                            {
                                Text(
                                    text = caption,
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        } else {
                            null
                        },
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = enabled,
                            isError = isError,
                            interactionSource = interactionSource,
                            shape = MaterialTheme.shapes.small,
                            colors = textFieldColors,
                        )
                    },
                ),
        )
    }
}

@Composable
fun AppOutlinedSecretTextField(
    state: TextFieldState,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    readOnly: Boolean = false,
    caption: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    AppOutlinedTextField(
        state = state,
        label = label,
        isError = isError,
        readOnly = readOnly,
        leadingIcon = leadingIcon,
        caption = caption,
        trailingIcon = {
            IconButton(
                onClick = { isPasswordVisible = !isPasswordVisible },
            ) {
                Icon(
                    painter =
                        if (isPasswordVisible) {
                            painterResource(R.drawable.visibility_24dp)
                        } else {
                            painterResource(R.drawable.visibility_off_24dp)
                        },
                    contentDescription = stringResource(R.string.toggle_password_visibility),
                )
            }
        },
        onKeyboardAction = onKeyboardAction,
        keyboardOptions = keyboardOptions,
        outputTransformation = if (isPasswordVisible) null else PasswordOutputTransformation(),
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun AppOutlinedSecretTextFieldPreview() {
    AppTheme {
        Surface {
            AppOutlinedSecretTextField(
                state = TextFieldState("qwerty"),
                label = "Password",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppOutlinedTextFieldPreview() {
    AppTheme {
        Surface {
            AppOutlinedTextField(
                state = TextFieldState("zalupa@mail.ru"),
                label = "Email",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppOutlinedTextFieldErrorPreview() {
    AppTheme {
        Surface {
            AppOutlinedTextField(
                state = TextFieldState("zalupa@mail.ru"),
                label = "Email",
                isError = true,
                caption = "Invalid email",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
            )
        }
    }
}
