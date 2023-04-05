package com.example.timer.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timer.model.ButtonType

@Composable
fun TimerButton(
    modifier: Modifier = Modifier,
    buttonType: ButtonType,
    enable: Boolean,
    onClickButton: () -> Unit
) {
    Box(
        modifier = modifier
            .width(100.dp)
            .height(100.dp)
            .clip(CircleShape)
            .background(buttonType.backgroundColor)
            .alpha(if (enable) 1.0f else 0.5f)
            .padding(5.dp)
            .clickable(
                enabled = enable,
                role = Role.Button,
                onClick = onClickButton
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Color.White)
        ) {}
        Column(
            modifier = Modifier
                .size(85.dp)
                .clip(CircleShape)
                .background(buttonType.backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(buttonType.titleId),
                color = if (buttonType == ButtonType.CANCEL) {
                    Color.White
                } else {
                    Color.Black
                },
                fontSize = 15.sp
            )
        }
    }
}

@Composable
@Preview
private fun TimerButtonPreview() {
    TimerButton(
        buttonType = ButtonType.POSE,
        enable = false,
        onClickButton = {}
    )
}