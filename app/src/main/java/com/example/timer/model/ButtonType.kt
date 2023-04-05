package com.example.timer.model

import androidx.compose.ui.graphics.Color
import com.example.timer.R
import com.example.timer.ui.theme.Gray
import com.example.timer.ui.theme.Green
import com.example.timer.ui.theme.Orange

enum class ButtonType(val titleId: Int, val backgroundColor: Color) {
    START(R.string.start, Green),
    POSE(R.string.pose, Orange),
    RESTART(R.string.restart, Green),
    CANCEL(R.string.cancel, Gray);
}