package com.sergey_y.simpletweets

import android.os.Bundle
import android.util.Log
import android.view.Choreographer
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sergey_y.simpletweets.data.ApiClient
import com.sergey_y.simpletweets.data.Tweet
import com.sergey_y.simpletweets.ui.AddItemsToColumn
import com.sergey_y.simpletweets.ui.animation.AnimationType
import com.sergey_y.simpletweets.ui.animation.BaseAnimation
import com.sergey_y.simpletweets.ui.animation.MosaicLayout
import com.sergey_y.simpletweets.ui.theme.SimpleTweetsTheme
import com.sergey_y.simpletweets.ui.theme.TwitterLightGray
import com.sergey_y.simpletweets.ui.tweet.Tweet
import com.sergey_y.simpletweets.ui.tweet.TweetDetails
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    private val particleCount = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        super.onCreate(savedInstanceState)
        val benchmarkKey = intent.getStringExtra("benchmarkKey") ?: "navigation"
        Log.d("MainActivity", benchmarkKey)
        setContent {
            SimpleTweetsTheme {
                val tweets by ApiClient.getTweets().collectAsState(initial = emptyList())
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (benchmarkKey) {
                        "list", "" -> TweetList(Modifier, tweets)
                        "item" -> SingleItemRecomposition(tweets)
                        "navigation" -> Navigation(tweets)
                        "canvas_particles" -> BaseAnimation(
                            particleCount = particleCount,
                            animationType = AnimationType.CANVAS
                        )
                        "layout_offset" -> BaseAnimation(
                            particleCount = particleCount,
                            animationType = AnimationType.LAYOUT_OFFSET
                        )
                        "layout_graphic_layer" -> BaseAnimation(
                            particleCount = particleCount,
                            animationType = AnimationType.LAYOUT_GRAPHIC_LAYER
                        )
                        "particle_custom_layout" -> BaseAnimation(
                            particleCount = particleCount,
                            animationType = AnimationType.CUSTOM_LAYOUT
                        )
                        "mosaic_layout" -> MosaicLayout()
                        "add_items" -> AddItemsToColumn(tweets = tweets)
                    }
                }
            }
        }
        launchIdlenessTracking()
    }

    @Composable
    private fun Navigation(tweets: List<Tweet>) {
        val navController = rememberAnimatedNavController()
        AnimatedNavHost(
            navController = navController,
            startDestination = "list",
            enterTransition = { _, _ -> EnterTransition.None },
            exitTransition = { _, _ -> fadeOut() }
        ) {
            composable("list") {
                TweetList(modifier = Modifier, tweets = tweets) { tweetId ->
                    navController.navigate("tweet/$tweetId")
                }
            }
            composable(
                "tweet/{id}",
                enterTransition = { _, _ -> slideInHorizontally(initialOffsetX = { it }) },
                exitTransition = { _, _ -> slideOutHorizontally(targetOffsetX = { it }) },
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { entry ->
                val tweet = tweets.find { it.id == entry.arguments?.getString("id") }
                if (tweet != null) {
                    TweetDetails(tweet = tweet)
                }
            }
        }
    }

    @Composable
    private fun SingleItemRecomposition(tweets: List<Tweet>) {
        if (tweets.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .semantics { contentDescription = "single_item" }
            ) {
                val tweet by produceState(initialValue = tweets.first()) {
                    tweets.forEach {
                        delay(50)
                        value = it
                    }
                }
                Tweet(tweet = tweet)
                if (tweet.id == tweets.last().id) {
                    Box(modifier = Modifier
                        .size(1.dp)
                        .semantics { contentDescription = "last_item" })
                }
            }
        }
    }

    @Composable
    private fun TweetList(
        modifier: Modifier,
        tweets: List<Tweet>,
        onItemClick: (id: String) -> Unit = {}
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .semantics { contentDescription = "tweet_lazy_list" },
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyTop = true,
                applyBottom = true,
            )
        ) {
            item(key = "list_start") {
                Box(modifier = Modifier
                    .size(1.dp)
                    .semantics { contentDescription = "list_start" })
            }
            itemsIndexed(items = tweets, key = { _, tweet -> tweet.id }) { idx, tweet ->
                Tweet(
                    tweet = tweet,
                    onClick = onItemClick
                )
                if (idx < tweets.lastIndex) {
                    Box(
                        modifier = Modifier
                            .height(0.5f.dp)
                            .fillMaxWidth()
                            .background(color = TwitterLightGray)
                    )
                }
            }
            item(key = "list_end") {
                Box(modifier = Modifier
                    .size(1.dp)
                    .semantics { contentDescription = "list_end" })
            }
        }
    }
}

internal fun ComponentActivity.launchIdlenessTracking() {
    val contentView: View = findViewById(android.R.id.content)
    val callback: Choreographer.FrameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            if (Recomposer.runningRecomposers.value.any { it.hasPendingWork }) {
                contentView.contentDescription = "COMPOSE-BUSY"
            } else {
                contentView.contentDescription = "COMPOSE-IDLE"
            }
            Choreographer.getInstance().postFrameCallback(this)
        }
    }
    Choreographer.getInstance().postFrameCallback(callback)
}