package com.sergey_y.simpletweets.data

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class Tweet(
    val id: String,
    val userNickname: String,
    val userFullName: String,
    val userAvatar: String,
    val text: String,
    val images: List<String>,
    val likes: Int,
    val retweets: Int,
    val messages: Int,
    val comments: List<Tweet>,
    val createdAt: Long,
    val retweet: Tweet?,
    val poll: Poll?
)

@Serializable
@Immutable
data class PollPosition(
    val text: String,
    val voted: Int
)

@Serializable
@Immutable
data class Poll(
    val isCompleted: Boolean,
    val totalVotes: Int,
    val positions: List<PollPosition>
)