package com.sergey_y.simpletweets.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.sergey_y.simpletweets.data.Tweet
import com.sergey_y.simpletweets.ui.animation.ANIMATION_TIMEOUT
import com.sergey_y.simpletweets.ui.tweet.Tweet
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddItemsToColumn(tweets: List<Tweet>) {
    if (tweets.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .semantics { contentDescription = "add_items_layout" }
        ) {
            val tweetList = remember {
                SnapshotStateList<Tweet>()
            }
            LaunchedEffect(Unit) {
                withTimeout(ANIMATION_TIMEOUT) {
                    while (true) {
                        tweetList.clear()
                        repeat(10) {
                            delay(100)
                            tweetList.add(tweets[it])
                        }
                    }
                }
            }

            tweetList.forEach { tweet ->
                key(tweet.id) {
                    var isVisible by remember(tweet.id) {
                        mutableStateOf(false)
                    }
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    ) {
                        Tweet(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(), tweet = tweet
                        )
                    }
                    LaunchedEffect(tweet.id) {
                        isVisible = true
                    }
                }
            }
        }
    }
}