@file:OptIn(ExperimentalCoilApi::class)
@file:Suppress("NOTHING_TO_INLINE")

package com.sergey_y.simpletweets.ui.tweet

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.PixelSize
import coil.transform.CircleCropTransformation
import com.sergey_y.simpletweets.data.Tweet
import com.sergey_y.simpletweets.ui.theme.*
import java.time.Instant
import java.util.concurrent.TimeUnit

private const val SHOW_ICONS = true

private val AvatarSizeLarge = 44.dp
private val AvatarSizeSmall = 22.dp

@Composable
fun Tweet(
    modifier: Modifier = Modifier,
    tweet: Tweet,
    compactMode: Boolean = false,
    monochromeBottomIcons: Boolean = false,
    onClick: (id: String) -> Unit = {}
) {
    val contentPadding = if (compactMode) 8.dp else 16.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick.invoke(tweet.id) }
            )
            .padding(contentPadding)
    ) {
        UserLine(tweet, compactMode)
        Column(
            modifier = Modifier
                .padding(start = if (compactMode) 0.dp else contentPadding)
                .padding(
                    start = if (compactMode) 0.dp else AvatarSizeLarge,
                    top = if (compactMode) AvatarSizeSmall else AvatarSizeLarge / 2
                )
                .fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            val textSize = if (compactMode) 12.sp else 14.sp
            Text(
                text = tweet.text,
                style = MaterialTheme.typography.body1.copy(fontSize = textSize)
            )

            if (tweet.images.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                ImageList(tweet.images, compactMode)
            }
            if (tweet.retweet != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Retweet(tweet.retweet)
            }

            if (tweet.poll != null) {
                Spacer(modifier = Modifier.height(8.dp))
                PollView(poll = tweet.poll, compactMode = compactMode)
            }

            if (compactMode.not()) {
                TweetFooter(tweet, monochromeBottomIcons)
            }
        }
    }
}

@Composable
inline fun Retweet(tweet: Tweet) {
    Tweet(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .border(1.dp, TwitterLightGray, RoundedCornerShape(6.dp))
            .padding(2.dp),
        tweet = tweet,
        compactMode = true
    )
}

@Composable
private fun UserLine(tweet: Tweet, compactMode: Boolean) {
    val nameColor = MaterialTheme.colors.onBackground
    val userString =
        remember(tweet.userFullName, tweet.userNickname, tweet.createdAt, compactMode, nameColor) {
            buildUserString(
                fullName = tweet.userFullName,
                nickName = tweet.userNickname,
                createdAt = tweet.createdAt,
                compactMode = compactMode,
                nameColor = nameColor
            )
        }
    Row {
        Avatar(userAvatar = tweet.userAvatar, compactMode = compactMode)
        Spacer(modifier = Modifier.width(if (compactMode) 8.dp else 16.dp))
        Text(text = userString, style = MaterialTheme.typography.subtitle2)
    }
}

@Composable
private fun TweetFooter(tweet: Tweet, monochromeBottomIcons: Boolean) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconWithText(
            icon = Icons.Outlined.Message,
            text = "${tweet.messages}",
            iconTint = if (monochromeBottomIcons) TwitterLightGray else TwitterBlue
        )
        IconWithText(
            icon = Icons.Outlined.Refresh,
            text = "${tweet.retweets}",
            iconTint = if (monochromeBottomIcons) TwitterLightGray else TwitterGreen
        )
        IconWithText(
            icon = Icons.Outlined.Favorite,
            text = "${tweet.likes}",
            iconTint = if (monochromeBottomIcons) TwitterLightGray else TwitterPink
        )
        IconWithText(
            icon = Icons.Outlined.Share,
            text = "",
            iconTint = if (monochromeBottomIcons) TwitterLightGray else TwitterLightGray
        )
    }
}

private fun buildUserString(
    fullName: String,
    nickName: String,
    createdAt: Long,
    compactMode: Boolean,
    nameColor: Color
) = buildAnnotatedString {
    withStyle(
        SpanStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = nameColor
        )
    ) {
        append(fullName)
    }
    append(" ")
    withStyle(
        SpanStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            color = TwitterDarkGray
        )
    ) {
        append(nickName)
        append(" • ")
        append(formattedTime(createdAt, compactMode))
    }
}

private fun formattedTime(createdAt: Long, compactMode: Boolean): String {
    val hours = if (compactMode) "h" else " hours"
    val minutes = if (compactMode) "min" else " minutes"
    val timeAgo = buildString {
        val now = Instant.now()
        val diff = now.minusMillis(createdAt).toEpochMilli()
        val hrs = TimeUnit.MILLISECONDS.toHours(diff)
        val minutesTime = if (hrs > 0) 0L else TimeUnit.MILLISECONDS.toMinutes(diff)
        if (hrs > 0) {
            when (hrs) {
                1L -> if (compactMode) append("1h ago") else append("an hour ago")
                else -> append("$hrs$hours ago")
            }
            append(" ")
        }
        if (minutesTime > 0) {
            when (minutesTime) {
                1L -> append("now")
                else -> append("$minutesTime$minutes ago")
            }
        }

        if (this.isBlank()) {
            append("now")
        }
    }
    return timeAgo
}

@Composable
fun Avatar(userAvatar: String, compactMode: Boolean) {
    val imgLayoutSizeDp = if (compactMode) AvatarSizeSmall else AvatarSizeLarge
    val imgLayoutSizePx = with(LocalDensity.current) {
        imgLayoutSizeDp.roundToPx()
    }
    Image(
        painter = rememberImagePainter(userAvatar) {
            setImageSize(imgLayoutSizePx, imgLayoutSizePx, compactMode)
            transformations(CircleCropTransformation())
        },
        contentDescription = null,
        modifier = Modifier.size(imgLayoutSizeDp).semantics { contentDescription = "avatar" }
    )
}

@Composable
fun ImageList(images: List<String>, compactMode: Boolean, imageRowHeight: Dp = if (compactMode) 125.dp else 150.dp) {
    when (images.size) {
        1 -> MonoImage(images.first(), imageRowHeight, compactMode)
        2 -> DuoImage(images[0], images[1], imageRowHeight, compactMode)
        3 -> TripleImage(
            imageHeight = imageRowHeight,
            imgA = images[0],
            imgB = images[1],
            imgC = images[2],
            compactMode = compactMode
        )
        4 -> QuadImage(
            imageHeight = imageRowHeight + 56.dp,
            imgA = images[0],
            imgB = images[1],
            imgC = images[2],
            imgD = images[3],
            compactMode = compactMode
        )
        else -> ImageRow(imageRowHeight, images, compactMode)
    }

}

private fun ImageRequest.Builder.setImageSize(width: Int, height: Int, compactMode: Boolean) {
    val qualityReducer = if (compactMode) LOW_IMAGE_QUALITY else HIGH_IMAGE_QUALITY
    size(
        PixelSize(
            (width * qualityReducer).toInt(),
            (height * qualityReducer).toInt()
        )
    )
}

@Composable
private fun IconWithText(icon: ImageVector, text: String, iconTint: Color) {

    CompositionLocalProvider(
        (LocalContentColor provides iconTint)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (SHOW_ICONS) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
            }
            if (text.isNotEmpty()) {
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = text, style = MaterialTheme.typography.caption.copy(fontSize = 14.sp))
            }
        }
    }
}