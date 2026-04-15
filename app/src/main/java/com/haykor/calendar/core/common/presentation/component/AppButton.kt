package com.haykor.calendar.core.common.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haykor.calendar.core.ui.theme.AppTheme

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable RowScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "ButtonScaleAnimation",
    )
    Button(
        onClick = onClick,
        interactionSource = interactionSource,
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(vertical = 14.dp),
        colors =
            ButtonDefaults.buttonColors().copy(
                containerColor = containerColor,
                contentColor = contentColor,
            ),
        modifier =
            modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
        content = content,
    )
}

@Preview()
@Composable
private fun AppButtonPreview() {
    AppTheme {
        AppButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Example button")
        }
    }
}
