package com.haykor.calendar.core.common.presentation.util

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer

class PasswordOutputTransformation(
    private val mask: Char = '\u2022', // bullet
) : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        replace(0, length, mask.toString().repeat(length))
    }
}
