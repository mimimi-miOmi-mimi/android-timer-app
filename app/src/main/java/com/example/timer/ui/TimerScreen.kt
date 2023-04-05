package com.example.timer.ui

import android.widget.NumberPicker
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timer.R
import com.example.timer.model.ButtonType
import com.example.timer.model.TimerViewModel
import com.example.timer.ui.component.TimerButton
import com.example.timer.ui.theme.Orange

@Composable
fun TimerScreen(
    viewModel: TimerViewModel = viewModel()
) {
    TimerScreenContent(
        viewModel.sweepPercentage,
        viewModel.remainingTime,
        viewModel.endTime,
        viewModel.isStartButtonEnable,
        viewModel.isTimerStarted,
        viewModel::onChangeTimerValue,
        viewModel::onClickCancel,
        viewModel::onClickPose,
        viewModel::startTimer
    )
}

@Composable
private fun TimerScreenContent(
    sweepAngle: Float,
    remainingTime: String,
    finishingTime: String,
    isStartButtonEnable: Boolean,
    isTimerStarted: Boolean,
    onChangeValue: (Int, Int, Int) -> Unit,
    onClickCancel: () -> Unit,
    onClickPose: () -> Unit,
    startTimer: () -> Unit
) {
    var startButtonType by remember { mutableStateOf(ButtonType.POSE) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 10.dp,
                vertical = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isTimerStarted) {
            Timer(
                sweepAngle,
                remaingingTime = remainingTime,
                finishingTime = finishingTime
            )
        } else {
            TimePicker(onChangeValue = onChangeValue)
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TimerButton(
                buttonType = ButtonType.CANCEL,
                enable = true
            ) {
                onClickCancel()
            }
            if (isTimerStarted) {
                TimerButton(
                    buttonType = startButtonType,
                    enable = true
                ) {
                    startButtonType = if (startButtonType == ButtonType.POSE) {
                        onClickPose()
                        ButtonType.RESTART
                    } else {
                        startTimer()
                        ButtonType.POSE
                    }
                }
            } else {
                TimerButton(
                    buttonType = ButtonType.START,
                    enable = isStartButtonEnable
                ) {
                    startTimer()
                }
            }
        }
    }
}

@Composable
private fun TimePicker(
    onChangeValue: (Int, Int, Int) -> Unit
) {
    var hour by remember { mutableStateOf(0) }
    var min by remember { mutableStateOf(0) }
    var sec by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // hour
        AndroidView(
            modifier = Modifier,
            factory = { context ->
                NumberPicker(context).also { numberPicker ->
                    numberPicker.maxValue = 99
                    numberPicker.minValue = 0
                    numberPicker.setOnValueChangedListener { _, _, newValue ->
                        hour = newValue
                        onChangeValue(hour, min, sec)
                    }
                    numberPicker.wrapSelectorWheel = false
                }
            }
        )
        Text(stringResource(R.string.hour))
        Spacer(modifier = Modifier.width(30.dp))
        // min
        AndroidView(
            modifier = Modifier,
            factory = { context ->
                NumberPicker(context).also { numberPicker ->
                    numberPicker.maxValue = 59
                    numberPicker.minValue = 0
                    numberPicker.setOnValueChangedListener { _, _, newValue ->
                        min = newValue
                        onChangeValue(hour, min, sec)
                    }
                    numberPicker.wrapSelectorWheel = false
                }
            }
        )
        Text(stringResource(R.string.min))
        Spacer(modifier = Modifier.width(30.dp))
        // sec
        AndroidView(
            modifier = Modifier,
            factory = { context ->
                NumberPicker(context).also { numberPicker ->
                    numberPicker.maxValue = 59
                    numberPicker.minValue = 0
                    numberPicker.setOnValueChangedListener { _, _, newValue ->
                        sec = newValue
                        onChangeValue(hour, min, sec)
                    }
                    numberPicker.wrapSelectorWheel = false
                }
            }
        )
        Text(stringResource(R.string.sec))
    }
}

@Composable
private fun Timer(
    sweepAngle: Float,
    remaingingTime: String,
    finishingTime: String
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(350.dp),
            onDraw = {
                drawArc(
                    color = Orange,
                    -90f,
                    sweepAngle,
                    false,
                    style = Stroke(20f)
                )
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = remaingingTime,
                fontSize = 77.sp,
                fontWeight = FontWeight.Light
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notifications),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = finishingTime,
                    fontSize = 25.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
@Preview
private fun TimerScreenPreview() {
    TimerScreenContent(
        355f,
        "14:58",
        "13:56",
        false,
        false,
        { _, _, _ -> },
        {},
        {},
        {}
    )
}
