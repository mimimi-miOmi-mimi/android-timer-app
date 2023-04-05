package com.example.timer.model

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.timer.extension.convertTimeText
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TimerViewModel : ViewModel() {
    // タイマー設定時間
    private var settingTime = 0L
    var countDownTimer: CountDownTimer? = null
    var isTimerStarted by mutableStateOf(false)
    var isStartButtonEnable by mutableStateOf(false)
    var remainingTime by mutableStateOf("")
    var remainingMilliSeconds by mutableStateOf(0L)
    var sweepPercentage by mutableStateOf(360f)

    // タイマー終了時刻
    var endTime by mutableStateOf("")

    fun onChangeTimerValue(newHour: Int, newMin: Int, newSec: Int) {
        remainingMilliSeconds = ((newHour * 3600 + newMin * 60 + newSec) * 1000).toLong()
        settingTime = remainingMilliSeconds
        isStartButtonEnable = remainingMilliSeconds != 0L
    }

    fun onClickCancel() {
        isTimerStarted = false
        isStartButtonEnable = false
        countDownTimer?.cancel()
    }

    fun onClickPose() {
        countDownTimer?.cancel()
    }

    fun startTimer() {
        // カウントダウン時間と終了時刻の設定
        remainingTime = (remainingMilliSeconds.toInt() / 1000).convertTimeText()
        val endDateTime = LocalDateTime.now()
            .plusSeconds(remainingMilliSeconds / 1000)
        endTime = endDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))

        isTimerStarted = true

        countDownTimer = object : CountDownTimer(remainingMilliSeconds, 100L) {
            override fun onTick(millisUntilFinished: Long) {
                remainingMilliSeconds = millisUntilFinished
                remainingTime = (remainingMilliSeconds.toInt() / 1000).convertTimeText()
                sweepPercentage = remainingMilliSeconds.toFloat() / settingTime.toFloat() * 360f
            }

            override fun onFinish() {
                isTimerStarted = false
                isStartButtonEnable = false
                countDownTimer = null
            }
        }
        countDownTimer?.start()
    }
}
