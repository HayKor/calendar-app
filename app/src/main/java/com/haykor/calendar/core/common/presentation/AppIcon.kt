package com.haykor.calendar.core.common.presentation

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.haykor.calendar.R

@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        painter = painterResource(R.drawable.baseline_calendar_month_24),
        contentDescription = stringResource(R.string.app_icon_description),
        tint = tint,
        modifier = modifier,
    )
}
