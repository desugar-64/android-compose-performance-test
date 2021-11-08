package com.sergey_y.simpletweets.ui.tweet

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sergey_y.simpletweets.data.Poll
import com.sergey_y.simpletweets.data.PollPosition
import com.sergey_y.simpletweets.ui.theme.*
import com.sergey_y.simpletweets.ui.theme.TwitterLightGray
import kotlin.math.roundToInt

@Composable
fun PollView(modifier: Modifier = Modifier, poll: Poll, compactMode: Boolean) {
    val buttonBorder = remember {
        BorderStroke(1.dp, TwitterBlue)
    }
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        for (pollPosition in poll.positions) {
            key(pollPosition, compactMode) {
                if (poll.isCompleted) {
                    val progress = pollPosition.voted / poll.totalVotes.toFloat()
                    TweetPollProgressLine(progress = progress, pollPosition.text, compactMode)
                } else {
                    TweetPollButton(buttonBorder, pollPosition.text, compactMode)
                }
            }
        }

        Text(text = "${poll.totalVotes} votes", color = TwitterDarkGray, fontSize = 12.sp)
    }
}

@Composable
private fun TweetPollProgressLine(
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    label: String,
    compactMode: Boolean
) {
    var pollProgress by rememberSaveable {
        mutableStateOf(0.0f)
    }
    val animatedProgress by animateFloatAsState(
        targetValue = pollProgress,
        animationSpec = tween(1500)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = ButtonDefaults.MinHeight + 16.dp)
            .drawBehind {
                val cornerSize = 6.dp.toPx()
                val width = (size.width * animatedProgress).coerceAtLeast(cornerSize)
                drawRoundRect(
                    color = TwitterVeryLightGray,
                    size = Size(
                        width = width,
                        height = size.height
                    ),
                    cornerRadius = CornerRadius(cornerSize, cornerSize)
                )
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            val textSize = if (compactMode) 12.sp else 14.sp
            val text = remember(progress.roundToInt()) {
                "${(progress * 100).roundToInt().coerceIn(0, 100)}%"
            }
            Text(
                modifier = Modifier
                    .weight(1.0f)
                    .padding(horizontal = 8.dp),
                text = label,
                color = MaterialTheme.colors.onBackground,
                fontSize = textSize
            )
            Text(
                text = text,
                color = MaterialTheme.colors.onBackground,
                fontSize = textSize
            )
        }
    }

    SideEffect {
        pollProgress = progress
    }
}

@Composable
private fun TweetPollButton(
    buttonBorder: BorderStroke,
    title: String,
    compactMode: Boolean
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(),
        shape = Shapes.large,
        border = buttonBorder,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = { /*TODO*/ }
    ) {
        val textSize = if (compactMode) 12.sp else 14.sp
        Text(text = title, color = TwitterBlue, fontSize = textSize)
    }
}

@Preview(name = "Poll not completed", device = Devices.PIXEL)
@Composable
private fun PollNotCompletedPreview() {
    MaterialTheme {
        val poll = Poll(
            isCompleted = false,
            totalVotes = 0,
            positions = listOf(
                PollPosition("Option 1", 0),
                PollPosition("Option 2", 0),
                PollPosition("Option 3", 0),
            )
        )
        PollView(modifier = Modifier.fillMaxWidth(), poll = poll, false)
    }
}

@Preview(name = "Poll completed", device = Devices.PIXEL)
@Composable
private fun PollCompletedPreview() {
    MaterialTheme {
        val poll = Poll(
            isCompleted = true,
            totalVotes = 100,
            positions = listOf(
                PollPosition("Option 0", 12),
                PollPosition("Option 1", 18),
                PollPosition("Option 2", 60),
                PollPosition("Option 3\ndddd\nddddf", 20),
            )
        )
        PollView(modifier = Modifier.fillMaxWidth(), poll = poll, false)
    }
}