package com.sergey_y.simpletweets.ui.tweet

import android.content.res.Configuration
import android.graphics.drawable.shapes.Shape
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.sergey_y.simpletweets.data.Tweet
import com.sergey_y.simpletweets.ui.theme.TwitterDarkGray
import com.sergey_y.simpletweets.ui.theme.TwitterLightGray
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TweetDetails(tweet: Tweet) {
    val padding = rememberInsetsPaddingValues(
        insets = LocalWindowInsets.current.systemBars,
        applyTop = true,
        applyBottom = true,
    )

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = padding) {
        item(key = tweet.id) {
            Header(tweet = tweet)
        }

        items(tweet.comments, key = { item -> item.id }) { tweet ->
            var visibility by rememberSaveable(tweet.id) {
                mutableStateOf(false)
            }
            AnimatedVisibility(
                visible = visibility,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(500)
                ) + fadeIn(
                    initialAlpha = 0.0f,
                    animationSpec = tween(500)
                )
            ) {
                Tweet(tweet = tweet, monochromeBottomIcons = true)
                FullWidthDivider()
            }

            LaunchedEffect(tweet.id) {
                visibility = true
            }
        }
    }

}

@Composable
private fun Header(tweet: Tweet) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
    ) {
        UserLine(tweet.userAvatar, tweet.userFullName, tweet.userNickname)

        Text(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), text = tweet.text)

        if (tweet.images.isNotEmpty()) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                val isLandLayout =
                    LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
                val imageRowHeight = if (isLandLayout) 150.dp else maxWidth
                ImageList(tweet.images, compactMode = false, imageRowHeight = imageRowHeight)
            }
        }

        if (tweet.poll != null) {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                PollView(poll = tweet.poll, compactMode = false)
            }
        }

        if (tweet.retweet != null) {
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Retweet(tweet = tweet.retweet)
            }
        }

        val formattedDate: String = remember(tweet.createdAt) {
            formatDate(tweet.createdAt)
        }
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
            text = formattedDate,
            color = TwitterLightGray,
            fontWeight = FontWeight.Light
        )

        FullWidthDivider()

        if (tweet.messages != 0 || tweet.retweets != 0 || tweet.likes != 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (tweet.messages != 0) {
                    FooterText(value = tweet.messages, title = "Comments")
                }
                if (tweet.retweets != 0) {
                    FooterText(value = tweet.retweets, title = "Retweets")
                }
                if (tweet.likes != 0) {
                    FooterText(value = tweet.likes, title = "Likes")
                }
            }
        }

        FullWidthDivider()

    }

}

@Composable
private fun UserLine(userAvatar: String, userFullName: String, userNickname: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avatar(userAvatar = userAvatar, compactMode = false)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = userFullName,
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = userNickname,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
private fun FooterText(value: Int, title: String) {
    val text = remember(value, title) {
        buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                append("$value")
            }
            append(" ")
            withStyle(SpanStyle(color = TwitterLightGray, fontWeight = FontWeight.Light)) {
                append(title)
            }
        }
    }
    Text(text = text)
}

@Composable
private fun FullWidthDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5f.dp)
            .background(TwitterLightGray)
    )
}

fun formatDate(createdAt: Long): String {
    val sdf = SimpleDateFormat("HH:mm • MMM dd yyyy", Locale.getDefault())
    return sdf.format(Date(createdAt))
}
